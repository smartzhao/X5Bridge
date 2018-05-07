package com.x5bridgelibrary;

import android.app.Application;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;
import com.x5bridgelibrary.jsbridge.BridgeHandler;
import com.x5bridgelibrary.jsbridge.CallBackFunction;
import com.x5bridgelibrary.listener.X5HandlerListener;


/**
 * zhaochong
 */
public class X5bridgeManager {

    private volatile static X5bridgeManager instance;  //静态变量

    public static X5HandlerListener x5HandlerListener;

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
     * 初始化入口
     *
     * @param context
     */
    public void initX5(Application context) {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(context.getApplicationContext(), cb);
    }



    public static X5HandlerListener getX5HandlerListener() {
        return x5HandlerListener;
    }

    public void setX5HandlerListener(X5HandlerListener x5HandlerListener) {
        this.x5HandlerListener = x5HandlerListener;
    }



}
