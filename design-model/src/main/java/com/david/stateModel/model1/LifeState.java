package com.david.stateModel.model1;

/**
 * Created by haojk on 3/2/17.
 */
public abstract class LifeState {

    //定义一个环境角色,也就是封装状态的变化引起的功能变化
    protected Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public abstract void open();

    public abstract void close();

    public abstract void run();

    public abstract void stop();
}
