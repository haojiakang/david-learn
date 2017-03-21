package com.david.strategyModel.model1;

/**
 * Created by haojk on 3/3/17.
 */
public class Client {

    public static void main(String[] args) {
        Context context;

        System.out.println("刚到吴国的时候开第一个");
        context = new Context(new BackDoor());
        context.operate();

        System.out.println("刘备乐不思蜀,开第二个");
        context = new Context(new GIvenGreenLight());
        context.operate();

        System.out.println("孙权来追兵,开第三个");
        context = new Context(new BlockEnemy());
        context.operate();
    }
}
