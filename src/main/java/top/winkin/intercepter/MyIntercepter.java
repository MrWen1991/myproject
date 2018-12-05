package top.winkin.intercepter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.winkin.anotation.Covert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @Description:
 * @Author: wenjiajia
 * @Data: 2018/12/5 5:14 PM
 */
public class MyIntercepter extends HandlerInterceptorAdapter {

    private static final Logger log = LoggerFactory.getLogger(MyIntercepter.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod method = (HandlerMethod) handler;
        Method m = method.getMethod();
        Covert covert = m.getAnnotation(Covert.class);
        Parameter[] parameters = m.getParameters();

        if (covert != null) {
            log.info("~~~~~~~~~~ this method was anotated by @Covert ~~~~~~~~~~~~");
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
