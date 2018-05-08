package com.x5bridgelibrary;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;
import com.x5bridgelibrary.jsbridge.BridgeHandler;
import com.x5bridgelibrary.jsbridge.BridgeWebView;
import com.x5bridgelibrary.jsbridge.CallBackFunction;
import com.x5bridgelibrary.listener.X5EventListener;
import com.x5bridgelibrary.listener.X5HandlerListener;
import com.x5bridgelibrary.services.X5NetService;
import com.x5bridgelibrary.x5view.X5Delegate;


/**
 * zhaochong
 */
public class X5bridgeManager {

    private volatile static X5bridgeManager instance;  //静态变量

    private static final String TAG = "X5bridgeManager";

    private Context context;

    private X5Delegate x5Delegate;

    private Activity activity;

    private X5EventListener x5EventListener;


    /**
     * 私有构造函数
     */
    private X5bridgeManager() {

    }

    /**
     * 单例RNBridgeManager
     *
     * @return
     */
    public static X5bridgeManager getInstance() {
        if (instance == null) {  //第一层校验
            synchronized (X5bridgeManager.class) {
                if (instance == null) {  //第二层校验
                    instance = new X5bridgeManager();
                }
            }
        }
        return instance;
    }

    /**
     * 预加载x5内核
     *
     * @param context
     */
    public void preInitX5Core(Context context) {
        setContext(context);
        Intent intent = new Intent(context, X5NetService.class);
        context.startService(intent);
    }

    /**
     * 初始化入口
     */
    public void initX5Web() {

        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

        if (!QbSdk.isTbsCoreInited()) {
            QbSdk.preInit(getContext().getApplicationContext(), null);// 设置X5初始化完成的回调接口
        }
        QbSdk.initX5Environment(getContext().getApplicationContext(), cb);

    }

    QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

        @Override
        public void onViewInitFinished(boolean arg0) {
            // TODO Auto-generated method stub
            Log.d(TAG, "onViewInitFinished: " + arg0);
        }

        @Override
        public void onCoreInitFinished() {
            // TODO Auto-generated method stub
            Log.d(TAG, "onCoreInitFinished: ");
        }

    };


    public X5HandlerListener getX5HandlerListener() {
        getX5Delegate();
        return x5HandlerListener;
    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    private X5HandlerListener x5HandlerListener = new X5HandlerListener() {

        @Override
        public void registerHandler(String handlerName, BridgeHandler handler) {
            x5Delegate.registerHandler(handlerName, handler);
        }

        @Override
        public void callHandler(String handlerName, String data, CallBackFunction callBack) {
            x5Delegate.callHandler(handlerName, data, callBack);
        }

        @Override
        public void send(String data) {
            x5Delegate.send(data);
        }

        @Override
        public void loadUrl(String jsUrl) {
            x5Delegate.loadUrl(jsUrl);
        }

    };


    public X5Delegate getX5Delegate() {
        x5Delegate = new X5Delegate(getActivity(), getX5EventListener().obtainLayoutId());
        return x5Delegate;
    }


    public Activity getActivity() {
        return activity;
    }

    public X5bridgeManager setActivity(Activity activity) {
        this.activity = activity;
        return this;
    }


    public X5EventListener getX5EventListener() {
        return x5EventListener;
    }

    public X5bridgeManager setX5EventListener(X5EventListener x5EventListener) {
        this.x5EventListener = x5EventListener;
        return this;
    }


}
