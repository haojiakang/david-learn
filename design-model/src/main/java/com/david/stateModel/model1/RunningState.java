package com.david.stateModel.model1;

/**
 * Created by haojk on 3/2/17.
 */
public class RunningState extends LifeState {

    @Override
    public void open() {

    }

    @Override
    public void close() {

    }

    @Override
    public void run() {
        System.out.println("电梯上下运行...");
    }

    @Override
    public void stop() {
        super.context.setLifeState(Context.stoppingState);
        super.context.getLifeState().stop();
    }
}
