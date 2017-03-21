package com.david.strategyModel.model2;

import java.util.Objects;

/**
 * Created by haojk on 3/3/17.
 */
public class Calculator {

    private final static String ADD_SYMBOL = "+";
    private final static String SUB_SYMBOL = "-";

    public int exec(int a, int b, String symbol) {
        int result = 0;
        if (Objects.equals(symbol, ADD_SYMBOL)) {
            result = this.add(a, b);
        } else if (Objects.equals(symbol, SUB_SYMBOL)) {
            result = this.sub(a, b);
        }
        return result;
    }

    private int add(int a, int b) {
        return a + b;
    }

    private int sub(int a, int b) {
        return a - b;
    }
}
