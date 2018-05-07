package com.x5bridgelibrary.jsbridge;

/**
 * Created by zhaochong on 4/5/18.
 */
public interface WebViewJavascriptBridge {

    public void send(String data);

    public void send(String data, CallBackFunction responseCallback);


}
