package com.x5bridge;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;

import com.x5bridgelibrary.X5bridgeManager;
import com.x5bridgelibrary.listener.X5HandlerListener;
import com.x5bridgelibrary.x5view.X5Activity;
import com.x5bridgelibrary.jsbridge.BridgeHandler;
import com.x5bridgelibrary.jsbridge.CallBackFunction;

import java.io.File;

public class MainActivity extends X5Activity {


    private static final String TAG = "MainActivity";


    @Nullable
    @Override
    protected int getLayoutID() {
        return R.layout.activity_main2;
    }

    @Nullable
    @Override
    protected Bundle getLaunchOptions() {

        Bundle bundle = new Bundle();
        bundle.putInt("x5webview", R.id.x5webview);
        return bundle;
    }

    private String getHtmlPath() {
        try {
            String[] names = getAssets().list("xm");
            if (names != null && names.length > 0) {
                return "file:///android_asset/xm/index.html";
            }
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                return "file://" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "xm" + File.separator + "index.html";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        X5bridgeManager.x5HandlerListener.loadUrl("file:///android_asset/demo.html", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });

        X5bridgeManager.x5HandlerListener.send("hello");

        X5bridgeManager.x5HandlerListener.registerHandler("submitFromWeb", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i(TAG, "handler = submitFromWeb, data from web = " + data);
                function.onCallBack("submitFromWeb exe, response data Native结果 from Java");
            }

        });

        X5bridgeManager.x5HandlerListener.callHandler("functionInJs", "dsds", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });
    }

}
