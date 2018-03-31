package com.david.learn;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置参数： -Xms20m -Xmx20m -XX:PermSize=8m -XX:MaxPermSize=8m
 */
public class StringOomMock {
    static String base = "string";

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String str = base + base;
            base = str;
            list.add(str.intern());
        }
    }
}
