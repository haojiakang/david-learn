package com.david.stateModel.model1;

/**
 * Created by haojk on 3/2/17.
 */
public class StoppingState extends LifeState {

    @Override
    public void open() {
        super.context.setLifeState(Context.openingState);
        super.context.getLifeState().open();
    }

    @Override
    public void close() {

    }

    @Override
    public void run() {
        super.context.setLifeState(Context.runningState);
        super.context.getLifeState().run();
    }

    @Override
    public void stop() {
        System.out.println("电梯停止了");
    }
}
