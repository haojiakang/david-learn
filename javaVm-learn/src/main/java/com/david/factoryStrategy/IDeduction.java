package com.david.factoryStrategy;

/**
 * Created by haojk on 1/25/17.
 */
public interface IDeduction {

    //扣款,提供交易和卡信息,进行扣款,并返回扣款是否成功
    boolean exec(Card card, Trade trade);
}
