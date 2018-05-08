package com.x5bridgelibrary.listener;

import com.x5bridgelibrary.jsbridge.BridgeHandler;
import com.x5bridgelibrary.jsbridge.CallBackFunction;

/**
 * Created by zhaochong on 4/5/18.
 */
public interface X5HandlerListener {

    /**
     * register handler,so that javascript can call it
     * 注册处理程序,以便javascript调用它
     *
     * @param handlerName handlerName
     * @param handler     BridgeHandler
     */

    void registerHandler(String handlerName, BridgeHandler handler);

    /**
     * call javascript registered handler
     * 调用javascript处理程序注册
     *
     * @param handlerName handlerName
     * @param data        data
     * @param callBack    CallBackFunction
     */
    void callHandler(String handlerName, String data, CallBackFunction callBack);

    /**
     * 默认handler 发送信息
     *
     * @param data
     */
    void send(String data);

    /**
     * 加载url
     *
     * @param jsUrl
     */
    void loadUrl(String jsUrl);
}