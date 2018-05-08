package com.x5bridge;

import android.app.Application;
import android.content.Intent;

import com.x5bridgelibrary.X5bridgeManager;
import com.x5bridgelibrary.services.X5NetService;


public class APPAplication extends Application {

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        X5bridgeManager.getInstance().preInitX5Core(this);
    }

}
