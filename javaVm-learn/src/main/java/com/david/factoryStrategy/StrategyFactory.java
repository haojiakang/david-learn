package com.david.factoryStrategy;

/**
 * Created by haojk on 1/25/17.
 */
public class StrategyFactory {

    //策略工厂
    public static IDeduction getDeduction(StrategyMan strategyMan) {
        IDeduction deduction = null;
        try {
            deduction = (IDeduction) Class.forName(strategyMan.getValue()).newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return deduction;
    }
}
