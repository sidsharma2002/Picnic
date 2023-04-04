package com.example.picnic.di.activity.usecases;

import androidx.appcompat.app.AppCompatActivity;
import com.example.picnic.common.permissions.RequestPermissionUseCase;
import com.example.picnic.screens.home.navigation.HomeNavigator;
import com.example.picnic.screens.home.navigation.HomeNavigatorImpl;

public class ActivityUseCaseFactory {

    private AppCompatActivity activity;
    private HomeNavigator homeNavigator;

    public ActivityUseCaseFactory(AppCompatActivity activity) {
        this.activity = activity;
        homeNavigator = new HomeNavigatorImpl(activity.getSupportFragmentManager());
    }

    public RequestPermissionUseCase getRequestPermissionUseCase() {
        return new RequestPermissionUseCase(activity);
    }

    public HomeNavigator getHomeNavigator() {
        return homeNavigator;
    }

}
