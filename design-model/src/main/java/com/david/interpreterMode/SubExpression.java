package com.david.interpreterMode;

import java.util.Map;

/**
 * Created by haojk on 1/15/17.
 */
public class SubExpression extends SymbolExpression {

    public SubExpression(Expression _left, Expression _right) {
        super(_left, _right);
    }

    //左右两个表达式相减
    @Override
    public int interpreter(Map<String, Integer> var) {
        return super.left.interpreter(var) - super.right.interpreter(var);
    }
}
