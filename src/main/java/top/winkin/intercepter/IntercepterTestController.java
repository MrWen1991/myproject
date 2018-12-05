package top.winkin.intercepter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.winkin.anotation.Covert;
import top.winkin.http.JsonResponse;

/**
 * @Description:
 * @Author: wenjiajia
 * @Data: 2018/12/5 5:32 PM
 */
@RequestMapping("/intecepter")
@Controller
public class IntercepterTestController {

    /**
     *  intercepterTest
     */
     @RequestMapping(value="/",method= RequestMethod.GET)
     @ResponseBody
     @Covert
     public JsonResponse a(String p){

        return new JsonResponse();
     }
}
