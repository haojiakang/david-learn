package com.david.stateModel.model2;

/**
 * Created by haojk on 3/2/17.
 */
public class OffLineState implements Function {

    @Override
    public void drive() {
        System.out.println("不在线的drive");
    }

    @Override
    public void run() {
        System.out.println("不在线的run");
    }

    @Override
    public void back() {
        System.out.println("不在线的back");
    }
}
