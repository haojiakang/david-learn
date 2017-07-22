package com.david.learn.test;

import org.junit.Test;

import java.util.logging.Logger;

/**
 * Created by jiakang on 2017/6/12.
 */
public class StringTest {

    @Test
    public void testString() {
        double x = Double.parseDouble("+1.0");
        System.out.println(x);
        int n = Integer.parseInt("-1");
        System.out.println(n);
        Logger.getGlobal().finest("Hahah");
    }
}
