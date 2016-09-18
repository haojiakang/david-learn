package com.david.lsp;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by David on 2016/9/18.
 */
public class Father {

    public Collection doSomething(HashMap map) {
        System.out.println("父类被执行...");
        return map.values();
    }
}
