package com.hyphenate.chatuidemo;

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
