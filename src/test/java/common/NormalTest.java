package common;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.winkin.util.ConfigPropertiesUtil;

/**
 * @Description:
 * @Author: wenjiajia
 * @Data: 2018/10/26 11:38 AM
 */
public class NormalTest {

    private static final Logger log = LoggerFactory.getLogger(NormalTest.class);
    @Test
    public void configTest() {
        String value = ConfigPropertiesUtil.getValue("site.url");
//        System.out.println("value ="+value);
        log.info("value={}",value);
        long l = 0X12;

    }
}
