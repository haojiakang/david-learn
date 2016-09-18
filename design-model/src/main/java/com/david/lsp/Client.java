package com.david.lsp;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by David on 2016/9/18.
 */
public class Client {

    public static void invoker() {
        //父类存在的地方，子类就应该能够存在
        Father f = new Father();
        HashMap map = new HashMap();
        f.doSomething(map);
    }

    public static void invoker2() {
        //父类存在的地方，子类就应该能够存在
        Son f = new Son();
        HashMap map = new HashMap();
        Map map2 = new HashMap<>();
        f.doSomething(map);
        f.doSomething(map2);
    }

    public static void main(String[] args) {
        invoker();
        invoker2();
    }
}
