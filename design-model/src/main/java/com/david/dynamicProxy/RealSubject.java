package com.david.dynamicProxy;

/**
 * Created by haojk on 12/23/16.
 */
public class RealSubject implements Subject {

    @Override
    public void doSomething(String str) {
        System.out.println("do something!---->" + str);
    }
}
