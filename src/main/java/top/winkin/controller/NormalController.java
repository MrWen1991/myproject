package top.winkin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.winkin.http.JsonResponse;

/**
 * @Description:
 * @Author: wenjiajia
 * @Data: 2018/11/20 11:04 AM
 */
@RequestMapping("/normal")
@Controller
public class NormalController {

    private static final Logger log = LoggerFactory.getLogger(NormalController.class);

    /**
     * test
     */
    @RequestMapping(value = "/")
    @ResponseBody
    public JsonResponse test(){
        return new JsonResponse("Ok ,this is the result !");
//        return "404";
    }
}
