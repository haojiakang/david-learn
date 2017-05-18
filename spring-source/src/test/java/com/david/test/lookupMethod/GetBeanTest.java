package com.david.test.lookupMethod;

/**
 * Created by jiakang on 2017/4/28.
 */
public abstract class GetBeanTest {

    public void showMe() {
        this.getBean().showMe();
    }

    public abstract User getBean();
}
