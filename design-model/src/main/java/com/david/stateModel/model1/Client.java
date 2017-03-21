package com.david.stateModel.model1;

/**
 * Created by haojk on 3/2/17.
 */
public class Client {

    public static void main(String[] args) {
        Context context = new Context();
        context.setLifeState(Context.closingState);
        context.open();
        context.close();
        context.run();
        context.stop();
    }
}
