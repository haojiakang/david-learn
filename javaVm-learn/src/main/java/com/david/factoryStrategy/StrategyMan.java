package com.david.factoryStrategy;

/**
 * Created by haojk on 1/25/17.
 */
public enum StrategyMan {

    SteadyDeduction("com.david.factoryStrategy.SteadyDeduction"),
    FreeDeduction("com.david.factoryStrategy.FreeDeduction");

    String value = "";

    StrategyMan(String _value) {
        this.value = _value;
    }

    public String getValue() {
        return this.value;
    }
}
