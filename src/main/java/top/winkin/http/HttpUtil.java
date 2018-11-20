package top.winkin.http;

import top.winkin.util.ConfigPropertiesUtil;
import top.winkin.util.JsonUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by sunjian on 15-1-29.
 */
public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static final String ENCODING = "UTF-8";
    private static final int DEFAULT_TIME_OUT = 10 * 1000;
    private static int TIME_OUT = DEFAULT_TIME_OUT;

    static {
        try {
            String configTime = ConfigPropertiesUtil.getValue("post.top.winkin.http.timeout.second");
            if (StringUtils.isNumeric(configTime)) {
                TIME_OUT = Integer.parseInt(configTime) * 1000;
            }
        } catch (Exception e) {
            //ignore
        }
    }


    public static String request(String url, Method method, Map<String, String> header, Map<String, String> form, Map requestBody) {
        return request(url, method, null, header, form, requestBody);
    }

    public static String request(String url, Method method, Map<String, String> credentials, Map<String, String> header, Map<String, String> form, Map requestBody) {
        return request(url, method, credentials, header, form, JsonUtil.dump(requestBody));
    }

    /**
     * @param url         请求地址，以http(s)打头。
     * @param method      POST或GET。
     * @param credentials 可为空，认证方式。（大部分时候都是空）
     * @param header      可为空，请求的header。
     * @param form        可为空，请求的表单数据。get请求时，将连接成字符串后放在url后。
     * @param requestBody 可为空，POST请求的raw数据。
     * @return 服务端响应字符串。仅响应体，无响应头。
     */
    public static String request(String url, Method method, Map<String, String> credentials, Map<String, String> header, Map<String, String> form, String requestBody) {
        CloseableHttpResponse response = doRequest(url, method, credentials, header, form, requestBody);
        try {
            return IOUtils.toString(response.getEntity().getContent(), ENCODING);
        } catch (Exception e) {
            throw new RuntimeException("转换" + url + "的响应失败", e);
        }
    }

    public static CloseableHttpResponse doRequest(String url, Method method, Map<String, String> credentials, Map<String, String> header, Map<String, String> form, String requestBody) {
        try {
            HttpRequestBase request = generateRequest(url, method, form, requestBody);
            if (header != null && !header.isEmpty()) {
                for (Map.Entry<String, String> headerEntry : header.entrySet()) {
                    request.addHeader(headerEntry.getKey(), headerEntry.getValue());
                }
            }
            return getHttpClient(url, credentials).execute(request);
        } catch (Exception e) {
            throw new RuntimeException("与[" + url + "]通信失败", e);
        }
    }


    public static JsonResponse getPostResponseWithHeader(String url, String requestBody, Map<String, String> headerMap) {
        return JsonUtil.load(postResponseWithHeader(url, requestBody, headerMap), JsonResponse.class);
    }

    public static String postResponseWithHeader(String url, String requestBody, Map<String, String> headerMap) {
        return request(url, Method.POST, null, headerMap, null, requestBody);
    }

    public static String postResponseWithParameterMap(String url, final Map<String, String> formParams) {
        return request(url, Method.POST, null, formParams, null);
    }

    public static String postResponseWithParameterMap(String url, final Map<String, String> formParams, Map<String, String> headerMap) {
        return request(url, Method.POST, headerMap, formParams, null);
    }


    public static String getGetResponseByUrl(String url) {
        return getGetResponseWithHeader(url, Collections.EMPTY_MAP);
    }

    public static String getGetResponseWithHeader(String url, Map<String, String> headerMap) {
        return request(url, Method.GET, headerMap, null, null);
    }

    public static String getGetResponseWithHeader(String url, Map<String, String> formParams, Map<String, String> headerMap) {
        return request(url, Method.GET, headerMap, formParams, null);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////


    private static DefaultHttpClient getHttpClient(String url, Map<String, String> credentials) throws Exception {
        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("top/winkin/http", PlainSocketFactory.getSocketFactory(), 80));
        registry.register(new Scheme("https", new TrustAllSSLSocketFactory(null, TIME_OUT), 443));

        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
        HttpConnectionParams.setConnectionTimeout(params, TIME_OUT);
        HttpConnectionParams.setSoTimeout(params, TIME_OUT);
        DefaultHttpClient httpclient = new DefaultHttpClient(new ThreadSafeClientConnManager(params, registry), params);

        if (credentials != null && credentials.size() > 0) {
            for (Map.Entry<String, String> entry : credentials.entrySet()) {
                httpclient.getCredentialsProvider().setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(entry.getKey(), entry.getValue()));
            }
        }
        return httpclient;
    }


    private static HttpRequestBase generateRequest(String url, Method method, Map<String, String> form, String requestBody) throws Exception {
        if (method == Method.GET) {
            if (form != null && !form.isEmpty()) {
                boolean first = true;
                StringBuilder urlWithParam = new StringBuilder(url);
                for (Map.Entry entry : form.entrySet()) {
                    if (first && !url.contains("?")) {
                        first = false;
                        urlWithParam.append("?");
                    } else {
                        urlWithParam.append("&");
                    }
                    urlWithParam.append(entry.getKey().toString()).append("=").append(entry.getValue().toString());
                }
                url = urlWithParam.toString();
            }
            return new HttpGet(url);
        } else if (method == Method.POST) {
            HttpPost post = new HttpPost(url);
            boolean formValue = false;
            boolean bodyValue = false;
            if (form != null && !form.isEmpty()) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(convert(form), ENCODING);
                post.setEntity(entity);
                formValue = true;
            }
            if (StringUtils.isNotBlank(requestBody) && !requestBody.equalsIgnoreCase("null")) {
                post.setEntity(new StringEntity(requestBody, ENCODING));
                bodyValue = true;
            }
            if (formValue && bodyValue) {
                throw new Exception("form和requestBody两者中，应至少有一个空值（null）");
            }
            return post;
        } else {
            throw new Exception("目前仅支持POST/GET方法");
        }
    }

    private static List<NameValuePair> convert(Map<String, String> map) {
        List<NameValuePair> formParams = new ArrayList();
        if (map != null) {
            Set<String> keys = map.keySet();
            for (String key : keys) {
                formParams.add(new BasicNameValuePair(key, map.get(key)));
            }
        }
        return formParams;
    }
}
