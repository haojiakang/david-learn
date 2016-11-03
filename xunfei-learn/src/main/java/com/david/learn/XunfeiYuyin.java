package com.david.learn;

import com.iflytek.cloud.speech.*;
import com.iflytek.util.DebugLog;

/**
 * Created by fsdevops on 10/18/16.
 */
public class XunfeiYuyin {

    private void testYuyin() {
        //appId为每个应用申请的APPID
        SpeechUtility.createUtility(SpeechConstant.APPID + "=58059d64");

        //1.创建SpeechRecognizer对象
        SpeechRecognizer mIat = SpeechRecognizer.createRecognizer();
        //2.设置听写参数，详见《iFlytek MSC Reference Manual》SpeechConstant类
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin");
        //3.开始听写
        mIat.startListening(mRecoListener);
    }

    //听写监听器
    private RecognizerListener mRecoListener = new RecognizerListener() {

        //音量值0-30
        @Override
        public void onVolumeChanged(int i) {

        }

        //开始录音
        @Override
        public void onBeginOfSpeech() {

        }

        //结束录音
        @Override
        public void onEndOfSpeech() {

        }

        //听写结果回调接口(返回Json格式结果,用户可参见附录);
        //一般情况下会通过onResults接口多次返回结果,完整的识别内容是多次结果的累加;
        //关于解析Json的代码可参见MscDemo中JsonParser类;
        //isLast等于true时会话结束。
        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            DebugLog.Log("Result: " + recognizerResult.getResultString());
        }

        //回话发生错误回调接口
        @Override
        public void onError(SpeechError speechError) {
            //获取错误码描述
            speechError.getErrorDescription(true);
        }

        //扩展用接口
        @Override
        public void onEvent(int i, int i1, int i2, String s) {

        }
    };

    public static void main(String[] args) {
        XunfeiYuyin xunfeiYuyin = new XunfeiYuyin();
        xunfeiYuyin.testYuyin();
    }
}
