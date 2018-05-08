package com.x5bridgelibrary.x5view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.KeyEvent;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebViewClient;
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

    private Activity x5Activity;
    private int layoutID;
    private Bundle launchOptions;
    private BridgeWebView webView;

    public BridgeWebView getWebView() {
        return webView;
    }

    public X5Delegate(Activity x5Activity, @IdRes
            int webviewId) {
        this.x5Activity = x5Activity;
        this.webView = (BridgeWebView) this.x5Activity.findViewById(webviewId);
    }

    public X5Delegate(Activity x5Activity, int layoutID, Bundle launchOptions) {
        this.layoutID = layoutID;
        this.x5Activity = x5Activity;
        this.launchOptions = launchOptions;

    }

    public void onCreate() {

        x5Activity.setContentView(layoutID);
        x5Activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webView.setDefaultHandler(new DefaultHandler());
            }
        });

    }

    public void loadUrl(final String jsUrl) {
        x5Activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(jsUrl);
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
        webView.clearView();
        x5Activity = null;

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
