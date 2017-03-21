package com.david.stateModel.model1;

/**
 * Created by haojk on 3/2/17.
 */
public class OpeningState extends LifeState {

    @Override
    public void open() {
        System.out.println("电梯门开启...");
    }

    @Override
    public void close() {
        //状态修改
        super.context.setLifeState(Context.closingState);
        //动作委托为ClosingState来执行
        super.context.getLifeState().close();
    }

    @Override
    public void run() {

    }

    @Override
    public void stop() {

    }
}
