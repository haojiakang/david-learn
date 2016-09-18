package com.david.lsp;

import java.util.Collection;
import java.util.Map;

/**
 * Created by David on 2016/9/18.
 */
public class Son extends Father {

    public Collection doSomething(Map map) {
        System.out.println("子类被执行...");
        return map.values();
    }
}
