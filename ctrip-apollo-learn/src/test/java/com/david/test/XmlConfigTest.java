package com.david.test;

import com.david.test.bean.TestXmlBean;
import com.google.common.util.concurrent.Uninterruptibles;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)//让测试运行于Spring测试环境
@ContextConfiguration("classpath:apollo-config.xml")
public class XmlConfigTest {

    @Resource
    private TestXmlBean testXmlBean;

    @Test
    public void test() {
        for (int i = 0; i < 100; i++) {
            System.out.println(testXmlBean);
            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        }
    }
}
