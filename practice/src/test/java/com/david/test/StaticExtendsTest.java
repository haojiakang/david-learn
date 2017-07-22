package com.david.test;

/**
 * Created by jiakang on 2017/7/12.
 */
public class StaticExtendsTest {

    public static void main(String[] args) {
        C c = new C();
        System.out.println(c.nonStaticStr);
        System.out.println(c.staticStr);
        c.staticMethod();//输出的结果都是父类中的非静态属性、静态属性和静态方法,推出静态属性和静态方法可以被继承

        System.out.println("-------------------------------");

        A c1 = new C();
        System.out.println(c1.nonStaticStr);
        System.out.println(c1.staticStr);
        c1.staticMethod();//结果同上，输出的结果都是父类中的非静态属性、静态属性和静态方法,推出静态属性和静态方法可以被继承

        System.out.println("-------------------------------");
        B b = new B();
        System.out.println(b.nonStaticStr);
        System.out.println(b.staticStr);
        b.staticMethod();

        System.out.println("-------------------------------");
        A b1 = new B();
        System.out.println(b1.nonStaticStr);
        System.out.println(b1.staticStr);
        b1.staticMethod();//结果都是父类的静态方法，说明静态方法不可以被重写，不能实现多态
        b1.nonStaticMethod();
    }

}