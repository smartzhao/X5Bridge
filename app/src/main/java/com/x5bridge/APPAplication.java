package com.x5bridge;

import android.app.Application;
import com.x5bridgelibrary.X5bridgeManager;


public class APPAplication extends Application {

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        X5bridgeManager.getInstance().initX5(this);

    }

}
