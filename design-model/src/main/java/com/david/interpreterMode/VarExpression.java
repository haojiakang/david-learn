package com.david.interpreterMode;

import java.util.Map;

/**
 * Created by haojk on 1/15/17.
 */
public class VarExpression extends Expression {

    private String key;

    public VarExpression(String _key) {
        this.key = _key;
    }

    //从map中取之
    @Override
    public int interpreter(Map<String, Integer> var) {
        return var.get(this.key);
    }
}
