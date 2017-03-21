package com.david.strategyModel.model1;

/**
 * Created by haojk on 3/3/17.
 */
public class Context {

    private IStrategy strategy;

    public Context(IStrategy strategy) {
        this.strategy = strategy;
    }

    public void operate() {
        strategy.operate();
    }
}
