package com.david.factoryStrategy;

/**
 * Created by haojk on 1/25/17.
 */
public class Trade {

    //交易编号
    private String tradeNo = "";

    //交易金额
    private int amount;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
