package com.hyphenate.chatuidemo;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {
    public static void postOkHttpRequest(String address, String postinfo, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody post= new FormBody.Builder()
                .add("sendtel",postinfo).build();
        Request request = new Request.Builder()
                .url(address).post(post).build();
        client.newCall(request).enqueue(callback);
    }
}
