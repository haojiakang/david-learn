package com.david.test;

/**
 * Created by jiakang on 2017/7/12.
 */
public class B extends A{//子类B
    public static String staticStr = "B改写后的静态属性";
    public String nonStaticStr = "B改写后的非静态属性";
    public static void staticMethod(){
        System.out.println("B改写后的静态方法");
    }
    @Override
    public void nonStaticMethod() {
        System.out.println("B改写后的非静态方法");
    }
}
