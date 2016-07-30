package com.david.learn;

import com.david.common.Common;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by Jackie on 2016/7/23.
 * VM args: -Xmx20M -XX:MaxDirectMemorySize=10M
 */
public class DirectMemoryOOM {

    public static void main(String[] args) throws IllegalAccessException {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        while (true) {
            unsafe.allocateMemory(Common._1MB);
        }
    }

}
