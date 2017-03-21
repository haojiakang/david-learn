package com.david.factoryStrategy;

/**
 * Created by haojk on 1/25/17.
 */
public class FreeDeduction implements IDeduction {

    @Override
    public boolean exec(Card card, Trade trade) {
        //直接从自由余额中扣除
        card.setFreeMoney(card.getFreeMoney() - trade.getAmount());
        return true;
    }
}
