package com.david.learn;

/**
 * Created by haojk on 1/19/17.
 */
public class OpusTool {

    static {
        System.load("/home/fsdevops/workspace/david-learn/opus-jni-learn/src/jni/libopustool.so");
    }

    public native String nativeGetString();

    public native int encodeWavFile(String inPath, String outPath);

    public native int decodeOpusFile(String inPath, String outPath);
}
