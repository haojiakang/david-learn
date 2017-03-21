package com.ione.opustool;

/**
 * Created by haojk on 1/16/17.
 */
public class OpusTool {

    public native String nativeGetString();

    public native int encode_wav_file(String in_path, String out_path);

    public native int decode_opus_file(String in_path, String out_path);
}
