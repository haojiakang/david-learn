package com.david.interpreterMode;


import java.util.Map;

/**
 * Created by haojk on 1/15/17.
 */
public class AddExpression extends SymbolExpression {

    public AddExpression(Expression _left, Expression _right) {
        super(_left, _right);
    }

    //把左右两个表达式运算的结果加起来
    @Override
    public int interpreter(Map<String, Integer> var) {
        return super.left.interpreter(var) + super.right.interpreter(var);
    }
}
