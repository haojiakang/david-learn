package com.david.learn;

/**
 * Created by haojk on 1/19/17.
 */
public class HelloJNI {

    static {
//        System.loadLibrary("/home/fsdevops/workspace/david-learn/jni-learn/target/classes/goodluck");
        System.load("/home/fsdevops/workspace/david-learn/jni-learn/target/classes/libgoodluck.so");
    }

    public native int get();

    public native void set(int i);

    public static void main(String[] args) {
        HelloJNI test = new HelloJNI();
        test.set(10);
        System.out.println(test.get());
    }
}
