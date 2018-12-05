package top.winkin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.winkin.aop.spring.UserDao;
import top.winkin.http.JsonResponse;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: wenjiajia
 * @Data: 2018/11/20 11:04 AM
 */
@RequestMapping("/normal")
@Controller
public class NormalController {

    private static final Logger log = LoggerFactory.getLogger(NormalController.class);

    @Resource
    private UserDao userDao;

    /**
     * test
     */
    @RequestMapping(value = "/")
    @ResponseBody
    public JsonResponse test(){
        Throwable ex = new Throwable();
        log.info(ex.toString());
        log.info(getStackTraceInfo(ex));

        return new JsonResponse("Ok ,this is the result !");
//        return "404";
    }

    /**
     *  apo
     */
     @RequestMapping(value="/aop",method= RequestMethod.GET)
     @ResponseBody
     public JsonResponse aop(){
         userDao.findUser();
        return new JsonResponse();
     }


    /**
     * 获取当前堆栈信息的字符串
     * @param ex
     * @return
     */
    private String getStackTraceInfo(Throwable ex) {
        StackTraceElement[] elements = ex.getStackTrace();
        StringBuffer traceInfo = new StringBuffer(ex.getMessage());
        for (StackTraceElement element:elements) {
            traceInfo.append("\n");
            traceInfo.append(element.toString());
        }
        return traceInfo.toString();
    }
}
