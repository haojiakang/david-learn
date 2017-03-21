package com.david.strategyModel.model1;

/**
 * Created by haojk on 3/3/17.
 */
public class BlockEnemy implements IStrategy {

    @Override
    public void operate() {
        System.out.println("孙夫人断后,挡住追兵!");
    }
}
