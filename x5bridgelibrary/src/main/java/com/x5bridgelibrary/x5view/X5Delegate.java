package com.x5bridgelibrary.x5view;

import android.os.Bundle;
import android.view.KeyEvent;

import com.x5bridgelibrary.X5bridgeManager;
import com.x5bridgelibrary.jsbridge.BridgeHandler;
import com.x5bridgelibrary.jsbridge.BridgeWebView;
import com.x5bridgelibrary.jsbridge.CallBackFunction;
import com.x5bridgelibrary.jsbridge.DefaultHandler;

/**
 * Created by zhaochong on 4/5/18.
 */

public class X5Delegate {
    private final String TAG = "X5Delegate";

    private X5Activity x5Activity;
    private int layoutID;
    private Bundle launchOptions;
    private BridgeWebView webView;


    public X5Delegate(X5Activity x5Activity, int layoutID, Bundle launchOptions) {
        this.layoutID = layoutID;
        this.x5Activity = x5Activity;
        this.launchOptions = launchOptions;

    }

    public void onCreate() {
        x5Activity.setContentView(layoutID);

        webView = x5Activity.findViewById(launchOptions.getInt("x5webview"));

        x5Activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webView.setDefaultHandler(new DefaultHandler());
            }
        });

//        X5bridgeManager.getInstance().setX5HandlerListener(x5Activity);

    }

    public void loadUrl(final String jsUrl, final CallBackFunction returnCallback) {
        x5Activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(jsUrl, returnCallback);
            }
        });
    }


    public void registerHandler(final String handlerName, final BridgeHandler handler) {

        x5Activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webView.registerHandler(handlerName, handler);
            }
        });
    }

    public void callHandler(final String handlerName, final String data, final CallBackFunction callBack) {
        x5Activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webView.callHandler(handlerName, data, callBack);
            }
        });
    }

    public void send(final String data) {
        x5Activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webView.send(data);
            }
        });
    }

    public void onDestory() {
        //释放资源
        if (webView != null) {
            x5Activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                //    webView.destroy();
                    webView.unregisterAllHandler();
                }
            });
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        final boolean[] isback = {false};
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            x5Activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (webView != null && webView.canGoBack()) {
                        webView.goBack();
                        isback[0] = !isback[0];
                    }
                }
            });
        }
        return isback[0];
    }
}
