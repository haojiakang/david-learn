package com.david.dynamicProxy;

/**
 * Created by haojk on 12/23/16.
 */
public class Client {

    public static void main(String[] args) {
        //定义一个主题
        Subject subject = new RealSubject();
        //定义主题的代理
        Subject proxy = DynamicProxy.newProxyInstance(subject);
        //代理的行为
        proxy.doSomething("Finish");
    }
}
