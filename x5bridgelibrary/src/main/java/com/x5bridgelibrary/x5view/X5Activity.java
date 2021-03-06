package com.x5bridgelibrary.x5view;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.ValueCallback;

import com.x5bridgelibrary.X5bridgeManager;
import com.x5bridgelibrary.jsbridge.BridgeHandler;
import com.x5bridgelibrary.jsbridge.CallBackFunction;
import com.x5bridgelibrary.listener.X5HandlerListener;

/**
 * Created by zhaochong on 4/5/18.
 */

public class X5Activity extends AppCompatActivity {


    int RESULT_CODE = 0;

    ValueCallback<Uri> mUploadMessage;

    private X5Delegate x5Delegate;

    protected X5Activity() {
        x5Delegate = createPreLoadDelegate();
    }

    private X5Delegate createPreLoadDelegate() {
        return new X5Delegate(this, getLayoutID(), getLaunchOptions());
    }


    /**
     * 子类重写，返回对应的布局
     *
     * @return
     */
    protected @Nullable
    int getLayoutID() {
        return 0;
    }

    /**
     * 获取启动所需参数
     *
     * @return
     */
    protected @Nullable
    Bundle getLaunchOptions() {
        return null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x5Delegate.onCreate();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == RESULT_CODE) {
            if (null == mUploadMessage) {
                return;
            }
            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
    }


    /**
     * 返回键监听
     *
     * @param keyCode
     * @param event
     * @return
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return x5Delegate.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        x5Delegate.onDestory();
        super.onDestroy();
    }


}


