package com.example.picnic.app;

import android.app.Application;

import com.example.picnic.di.application.AppCompositionRoot;

public class PicnicApp extends Application {

    private AppCompositionRoot appCompositionRoot;

    @Override
    public void onCreate() {
        super.onCreate();
        appCompositionRoot = new AppCompositionRoot(this);
    }

    public AppCompositionRoot getAppCompositionRoot() {
        return appCompositionRoot;
    }
}
