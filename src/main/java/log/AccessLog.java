package log;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConfigPropertiesUtil;
import util.JsonUtil;
import http.RequestUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class AccessLog {
    private static final String SESSION_COOKIE_NAME = "JSESSIONID";
    public static final String SESSION_USER_ID_KEY = "userId";
    public static final String ACCESS_LOG_KEY = "ACCESS_LOG";

    public static final String STATUS_KEY = "accesslog.status";
    public static final String EXCEPTION_KEY = "accesslog.exception";

    private static final String IGNORE_HEADER_NAMES = "User-Agent,X-Real-Ip,X-Forwarded-For,X-Forwarded-Proto,geo," +
            "Connection,Accept-Encoding,Cookie,Host," +
            "Pragma,Cache-Control,Accept-Language,Accept-Encoding,Referer";
    private static final Set<String> excludeHeaderNames = new HashSet<>(
            Arrays.asList(IGNORE_HEADER_NAMES.toUpperCase().split(","))
    );

    private static Logger logger = LoggerFactory.getLogger(AccessLog.class);

    private static ThreadLocal<AccessLog> holder = new ThreadLocal<AccessLog>();

    private final HttpServletRequest request;
    private final DateTime startTime;
    private DateTime endTime;
    private String userId;

    private Throwable ex;

    private Map<String, String> appVars;
    private HttpServletResponse response;

    //在写入日志的时候是否忽略请求的参数，默认是不忽略
    private static boolean ignoreParam;


    static {
        try {
            ignoreParam = ConfigPropertiesUtil.getBoolean("access.log.ignore.param");
        } catch (Throwable ignored) {
        }

    }

    public AccessLog(HttpServletRequest request) {
        this.request = request;
        startTime = DateTime.now();
        request.setAttribute(ACCESS_LOG_KEY, this);

        buildUserId();
        holder.set(this);
    }

    private void buildUserId() {
        if (isNormalUserId(userId)) {
            return;
        }
        Object userIdObj = request.getAttribute(SESSION_USER_ID_KEY);
        if (!isNormalUserId(userIdObj)) {
            userIdObj = request.getSession(true).getAttribute(SESSION_USER_ID_KEY);
        }
        if (isNormalUserId(userIdObj)) {
            userId = userIdObj.toString();
        }
    }

    private boolean isNormalUserId(Object userId) {
        if (userId == null) {
            return false;
        }
        if (userId instanceof Number) {
            return ((Number) userId).longValue() > 0;
        }
        String string = ObjectUtils.toString(userId);
        if (StringUtils.isBlank(string)) {
            return false;
        }
        try {
            return Long.parseLong(string) > 0;
        } catch (Exception e) {
            //ignore
        }
        return true;
    }

    public void end(HttpServletResponse response, Exception ex) {
        holder.set(null);

        this.response = response;
        if (request.getAttribute(EXCEPTION_KEY) != null) {
            ex = (Exception) request.getAttribute(EXCEPTION_KEY);
        }
        this.ex = ex;

        buildUserId();//login时，userId只有在这里才有
        endTime = DateTime.now();

        String json = toJson(ignoreParam);
        logger.info(json);
    }

    private HashMap<String, String> buildCookies() {
        Cookie[] httpCookies = request.getCookies();
        if (httpCookies == null) {
            return null;
        }

        HashMap<String, String> cookies = new HashMap<>();
        for (Cookie cookie : httpCookies) {
            cookies.put(cookie.getName(), cookie.getValue());
        }
        return cookies;
    }

    private String getSessionId() {
        String fromSession = request.getSession(false) == null ? null : request.getSession(false).getId();
        String fromCookie = null;
        Cookie cookie = getCookie(SESSION_COOKIE_NAME);
        if (cookie != null) {
            fromCookie = cookie.getValue();
        }
        if (StringUtils.equalsIgnoreCase(fromCookie, fromSession) || StringUtils.isBlank(fromCookie)) {
            return fromSession;
        } else {
            return fromSession + "," + fromCookie;
        }
    }

    private Cookie getCookie(String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie;
            }
        }
        return null;
    }


    private String toJson(boolean ignoreParam) {
        double duration = (endTime.getMillis() - startTime.getMillis()) / 1000.0;//seconds

        Map<String, Object> data = new HashMap<>();

        data.put("queryString", RequestUtil.getQueryString(request));
        data.put("startTime", startTime.toString());
        data.put("endTime", endTime.toString());
        data.put("duration", duration);
        data.put("ip", request.getRemoteAddr());
        data.put("serverIp", request.getLocalAddr());
        data.put("scheme", request.getScheme());
        data.put("method", request.getMethod());
        data.put("path", request.getRequestURI());
        data.put("sessionId", getSessionId());
        data.put("cookies", buildCookies());
//        data.put("cookie", request.getHeader("Cookie"));
        data.put("host", request.getHeader("Host"));
        data.put("referer", request.getHeader("Referer"));
        data.put("userAgent", request.getHeader("User-Agent"));
        if (!ignoreParam) {
            data.put("params", RequestUtil.getParameterString(request));
            data.put("params2", RequestUtil.getParameterArray(request));
        }
        data.put("thread", Thread.currentThread().getName());

        Object status = request.getAttribute(STATUS_KEY);
        if (status == null) {
            status = response.getStatus();
        }
        data.put("status", status);

        if (ex != null) {
            Map<String, Object> exceptionData = new HashMap<>();
            exceptionData.put("message", ex.getMessage());
            exceptionData.put("stackTrace", ex.getStackTrace());
            data.put("exception", exceptionData);
        }

        if (appVars != null) {
            data.put("appVars", appVars);
        }

        data.put("userId", userId);

        Map<String, String> validHeaders = extractValidHeaders(request);
        if (!validHeaders.isEmpty()) {
            data.put("headers", validHeaders);
        }

        return JsonUtil.dump(data);
    }

    private Map<String, String> extractValidHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            if (excludeHeaderNames.contains(name.toUpperCase())) {
                continue;
            }
            String value = request.getHeader(name);
            headers.put(name, value);
        }
        return headers;
    }

    public static void setAppVar(String name, String value) {
        AccessLog accessLog = holder.get();
        if (accessLog == null) {
            return;
        }

        if (accessLog.appVars == null) {
            accessLog.appVars = new HashMap<>();
        }
        accessLog.appVars.put(name, value);
    }
}
