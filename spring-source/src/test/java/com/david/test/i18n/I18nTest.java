package com.david.test.i18n;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.MessageFormat;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by jiakang on 2017/7/15.
 */
public class I18nTest {

    @Test
    public void i18nTest() {
        String[] configs = {"i18ntest.xml"};
        ApplicationContext ctx = new ClassPathXmlApplicationContext(configs);

        Object[] params = {"John", new GregorianCalendar().getTime()};

        String str1 = ctx.getMessage("test", params, Locale.US);
        String str2 = ctx.getMessage("test", params, Locale.CHINA);


        System.out.println(str1);
        System.out.println(str2);
    }

    @Test
    public void before() {
        Object params = new Object[]{"John", new GregorianCalendar().getTime(), 1.0E3};
        String pattern1 = "{0}你好！你于{1}在工商银行存入{2}元。";
        String pattern2 = "At {1,time,short} On {1,date,long}, {0} paid {2,number,currency}.";
        String format = MessageFormat.format(pattern1, params);
        String format1 = MessageFormat.format(pattern2, params);
        System.out.println(format);
        System.out.println(format1);
    }
}
