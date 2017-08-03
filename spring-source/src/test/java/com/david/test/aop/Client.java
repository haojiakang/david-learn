package com.david.test.aop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by jiakang on 2017/7/7.
 */
public class Client {

    @Test
    public void testAop() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("aspectTest.xml");
        TestBean testBean = (TestBean) ctx.getBean("test");
        testBean.test();
    }

    @Test
    public void testUserManager() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        UserManager userManager = (UserManager) ctx.getBean("userManager");
        System.out.println(userManager);
    }

    @Test
    public void loadInTime() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("loadintime.xml");
    }
}
