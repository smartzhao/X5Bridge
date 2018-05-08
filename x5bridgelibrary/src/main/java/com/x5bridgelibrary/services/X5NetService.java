package com.x5bridgelibrary.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.tencent.smtt.sdk.QbSdk;
import com.x5bridgelibrary.X5bridgeManager;

public class X5NetService extends IntentService {

    public static final String TAG = "X5NetService";

    public X5NetService() {
        super(TAG);
    }

    public X5NetService(String name) {
        super(TAG);
    }

    @Override
    public void onHandleIntent(@Nullable Intent intent) {
        X5bridgeManager.getInstance().initX5Web();
    }


}