package com.example.hardikdesaii.contentresolverdemo;

import android.app.Application;

/**
 * Created by HardikDesaii on 26/04/17.
 */

public class AppClass extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        new SharedPrefHelper(this);
    }
}
