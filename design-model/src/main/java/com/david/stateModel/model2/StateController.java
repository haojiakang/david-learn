package com.david.stateModel.model2;

/**
 * Created by haojk on 3/2/17.
 */
public class StateController implements State {

    private Function function;

    public void setFunction(Function function) {
        this.function = function;
    }

    @Override
    public void offLine() {
        setFunction(new OffLineState());
    }

    @Override
    public void onLine() {
        setFunction(new OnLineState());
    }

    public void drive() {
        function.drive();
    }

    public void run() {
        function.run();
    }

    public void back() {
        function.back();
    }
}
