package com.example.picnic.di.application.usecases;

import com.example.picnic.app.PicnicApp;
import com.example.picnic.common.permissions.RequestPermissionUseCase;
import com.example.picnic.screens.home.FetchLocallyStoredPhotosUseCase;

public class UseCaseFactory {

    private PicnicApp appContext;

    public UseCaseFactory(PicnicApp appContext) {
        this.appContext = appContext;
    }

    public FetchLocallyStoredPhotosUseCase getFetchLocallyStoredPhotosUseCase() {
        return new FetchLocallyStoredPhotosUseCase(appContext);
    }
}
