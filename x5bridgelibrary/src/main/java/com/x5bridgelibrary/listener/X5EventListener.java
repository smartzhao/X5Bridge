package com.x5bridgelibrary.listener;

import android.graphics.Bitmap;
import android.support.annotation.IdRes;


public interface X5EventListener {

    @IdRes
    int obtainLayoutId();

    void onPageStarted(String url, Bitmap favicon);

    void onPageFinished(String url);
/*
    boolean shouldOverrideUrlLoading(String url);

    boolean shouldOverrideUrlLoading(WebResourceRequest request);*/
}
