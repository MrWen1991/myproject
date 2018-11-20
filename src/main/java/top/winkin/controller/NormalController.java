package top.winkin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description:
 * @Author: wenjiajia
 * @Data: 2018/11/20 11:04 AM
 */
@RequestMapping("/normal")
public class NormalController {

    private static final Logger log = LoggerFactory.getLogger(NormalController.class);

    /**
     * test
     */
    @RequestMapping("/")
    public String test(){
        return "400";
    }
}
