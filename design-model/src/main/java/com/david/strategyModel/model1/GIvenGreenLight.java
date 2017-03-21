package com.david.strategyModel.model1;

/**
 * Created by haojk on 3/3/17.
 */
public class GIvenGreenLight implements IStrategy {

    @Override
    public void operate() {
        System.out.println("求吴国太开绿灯,放行!");
    }
}
