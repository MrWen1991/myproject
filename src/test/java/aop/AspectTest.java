package aop;

import base.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.winkin.aop.spring.UserDao;

/**
 * @Description:
 * @Author: wenjiajia
 * @Data: 2018/11/21 4:53 PM
 */
public class AspectTest extends BaseTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void aopTest() {
//        userDao.addUser(555);
        userDao.findUser();
    }
}
