package com.david.lsp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackie on 2016/7/23.
 * VM args: -XX:PermSize=10M -XX:MaxPermSize=10M
 */
public class RuntimeConstantPoolOOM {

    public static void main(String[] args) {
        RuntimeConstantPoolOOM oom = new RuntimeConstantPoolOOM();
//        oom.test01();
        oom.test02();
    }

    private void test01() {
        //使用List保持着常量池的引用，避免Full GC回收常量池行为
        List<String> list = new ArrayList<>();
        //10M的PermSize在integer范围内足够产生OOM了
        int i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }
    }

    private void test02() {
        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);
        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
    }

}
