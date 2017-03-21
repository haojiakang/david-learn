package com.ione.opustool;

/**
 * Created by haojk on 1/16/17.
 */
public class Client {

    public static void main(String[] args) {
        OpusTool opusTool = new OpusTool();
        String str = opusTool.nativeGetString();
        System.out.println(str);
    }
}
