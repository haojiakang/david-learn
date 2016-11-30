package com.david.test;

import org.junit.Test;

import java.lang.reflect.Field;

/**
 * Created by haojk on 11/30/16.
 */
public class TestInteger {

    @Test
    public void testInteger() throws Exception {
        Class cache = Integer.class.getDeclaredClasses()[0];
        System.out.println(cache);
        Field myCache = cache.getDeclaredField("cache");
        myCache.setAccessible(true);
        Integer[] newCache = (Integer[]) myCache.get(cache);
        newCache[132] = newCache[134];

        int a = 2;
        int b = a + a;
        System.out.printf("%d + %d = %d", a, a, b);
    }
}
