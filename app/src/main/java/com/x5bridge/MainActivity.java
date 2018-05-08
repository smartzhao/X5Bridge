package com.x5bridge;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.x5bridgelibrary.X5bridgeManager;
import com.x5bridgelibrary.jsbridge.BridgeHandler;
import com.x5bridgelibrary.jsbridge.CallBackFunction;
import com.x5bridgelibrary.listener.X5EventListener;
import com.x5bridgelibrary.listener.X5HandlerListener;


public class MainActivity extends AppCompatActivity implements X5EventListener, View.OnClickListener {

    private Button button;
    private static final String TAG = "MainActivity";
    private X5HandlerListener x5HandlerListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        initX5HandlerListener();

        x5HandlerListener.loadUrl("file:///android_asset/demo.html");

        x5HandlerListener.registerHandler("submitFromWeb", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d(TAG, "handler = submitFromWeb, data from web = " + data);
                function.onCallBack("submitFromWeb exe, response data Native结果 from Java");
            }

        });

        x5HandlerListener.callHandler("functionInJs", "dsds", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.d(TAG, "onCallBack functionInJs:  " + data);
            }
        });

        x5HandlerListener.send("hello");

    }

    @Override
    public void onClick(View v) {
        if (button.equals(v)) {
            x5HandlerListener.callHandler("functionInJs", "data from Java", new CallBackFunction() {

                @Override
                public void onCallBack(String data) {
                    // TODO Auto-generated method stub
                    Log.i(TAG, "reponse data from js " + data);
                }

            });
        }

    }

    @Override
    public int obtainLayoutId() {
        return R.id.x5webview;
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {
        Log.d(TAG, "onPageStarted: " + url);
    }

    @Override
    public void onPageFinished(String url) {
        Log.d(TAG, "onPageFinished: " + url);
    }

    public X5HandlerListener initX5HandlerListener() {
        x5HandlerListener = X5bridgeManager
                .getInstance()
                .setX5EventListener(this)
                .setActivity(MainActivity.this)
                .getX5HandlerListener();
        return x5HandlerListener;
    }
}
