package com.example.picnic.di.activity.usecases;

import androidx.appcompat.app.AppCompatActivity;
import com.example.picnic.common.permissions.RequestPermissionUseCase;

public class ActivityUseCaseFactory {

    private AppCompatActivity activity;

    public ActivityUseCaseFactory(AppCompatActivity activity) {
        this.activity = activity;
    }

    public RequestPermissionUseCase getRequestPermissionUseCase() {
        return new RequestPermissionUseCase(activity);
    }

}
