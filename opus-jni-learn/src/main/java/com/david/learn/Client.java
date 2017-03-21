package com.david.learn;

/**
 * Created by haojk on 1/19/17.
 */
public class Client {

    public static void main(String[] args) {
        OpusTool opusTool = new OpusTool();
        String str = opusTool.nativeGetString();
        System.out.println(str);

        int i = opusTool.encodeWavFile("doc/test.wav", "/doc/test.opus");
        System.out.println(i);
    }
}
