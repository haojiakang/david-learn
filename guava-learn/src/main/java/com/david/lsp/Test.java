package com.david.lsp;

/**
 * Created by haojk on 5/25/16.
 */
public class Test {

    private void test01(){
        User user = new User("Jackie", "123456", "alksdfj@sc.com", 23);
        System.out.println(user.toString());
        System.out.println("******************");
        System.out.println(user.toString2());
    }

    private void test02(){
        Type type = Type.E;
        System.out.println(type.name());
    }

    public static void main(String[] args){
        Test t =  new Test();
//        t.test01();
        t.test02();
    }


}
