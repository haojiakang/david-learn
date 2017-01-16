package com.david.dynamicProxy;

/**
 * Created by haojk on 12/23/16.
 */
public class BeforeAdvice implements IAdvice {

    @Override
    public void exec() {
        System.out.println("我是前置通知,我被执行了!");
    }
}
