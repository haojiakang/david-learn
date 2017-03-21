package com.david.stateModel.model2;

/**
 * Created by haojk on 3/2/17.
 */
public class Client {

    public static void main(String[] args) {
        StateController controller = new StateController();

        //设置状态
        controller.onLine();

        //省去if-else结构
        controller.run();
        controller.drive();
        controller.back();
    }
}
