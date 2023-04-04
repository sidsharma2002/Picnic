package com.example.picnic.common.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.Display;
import android.view.WindowInsets;

import com.example.picnic.app.PicnicApp;

public class ScreenUtils {

    private final Context context;

    public ScreenUtils(PicnicApp context) {
        this.context = context;
    }

    public int getNavBarHeight(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return activity.getWindowManager()
                    .getCurrentWindowMetrics()
                    .getWindowInsets()
                    .getInsets(WindowInsets.Type.navigationBars())
                    .bottom;
        } else {
            return 0;
        }
    }

}
