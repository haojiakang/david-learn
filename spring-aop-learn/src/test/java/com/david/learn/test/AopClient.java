package com.david.learn.test;

import com.david.learn.Math;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by jiakang on 2017/10/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)//让测试运行于Spring测试环境
@ContextConfiguration("classpath:aop01.xml")
public class AopClient {

    @Resource
    private Math math;

    @Test
    public void test() {
        int n1 = 100, n2 = 0;
        math.add(n1, n2);
        math.sub(n1, n2);
        math.mut(n1, n2);
        int div = math.div(n1, n2);
        System.out.println("done, div:" + div);
    }
}
