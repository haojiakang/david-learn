#include "com_david_learn_OpusTool.h"

JNIEXPORT jstring JNICALL Java_com_david_learn_OpusTool_nativeGetString(JNIEnv *env, jobject obj) {
  return (*env) -> NewStringUTF(env, "Hello Opus");
}

JNIEXPORT jint JNICALL Java_com_david_learn_OpusTool_encodeWavFile(JNIEnv *env, jobject obj, jstring wav_path, jstring opus_path) {
  return 0;
}

JNIEXPORT jint JNICALL Java_com_david_learn_OpusTool_decodeOpusFile(JNIEnv *env, jobject obj, jstring opus_path, jstring wav_path) {
  return 0;
}