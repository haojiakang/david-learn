package com.david.learn.spring;

import com.david.common.util.Consoles;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by jiakang on 2018/4/27
 *
 * @author jiakang
 */
public class SpringTest {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("userService-test.xml");
        UserService userService = context.getBean("userService", UserService.class);
        UserInfo userInfo = userService.getUserInfo(2);
        Consoles.info("userInfo:{}", userInfo);
    }
}