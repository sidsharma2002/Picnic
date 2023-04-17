package com.example.picnic.common.utils;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
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

    public boolean isDarkMode() {
        int nightModeFlags = context.getResources().getConfiguration().uiMode &
                Configuration.UI_MODE_NIGHT_MASK;

        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
    }

    public boolean isLightMode() {
        return !isDarkMode();
    }

    // The gesture threshold expressed in dp
    private final float GESTURE_THRESHOLD_DP = 16.0f;


    public float getPxFromDp(int dp) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
