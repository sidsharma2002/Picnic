package com.example.picnic.screens.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.picnic.R;
import com.example.picnic.app.PicnicApp;
import com.example.picnic.common.concurrency.UiThreadPoster;
import com.example.picnic.common.utils.ScreenUtils;
import com.example.picnic.screens.onboarding.OnBoardingActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // getWindow().setNavigationBarColor(getResources().getColor(R.color.default_blue));

        TextView heading = findViewById(R.id.tv_heading);

        heading.animate()
                .alphaBy(1f)
                .setStartDelay(200L)
                .setDuration(1500)
                .start();

        UiThreadPoster uiThreadPoster = ((PicnicApp) getApplication())
                .getAppCompositionRoot()
                .getUiThreadPoster();

        uiThreadPoster.postDelayed(() -> {
            Intent in = new Intent(this, OnBoardingActivity.class);
            startActivity(in);
            finish();
        }, 2000L);
    }
}
