package com.example.picnic.di.application.usecases;

import com.example.picnic.app.PicnicApp;
import com.example.picnic.common.concurrency.BgThreadPoster;
import com.example.picnic.common.concurrency.UiThreadPoster;
import com.example.picnic.screens.home.usecases.FetchPhotosFromStorageUseCase;

public class UseCaseFactory {

    private PicnicApp appContext;
    private BgThreadPoster bgThreadPoster;
    private UiThreadPoster uiThreadPoster;

    public UseCaseFactory(PicnicApp appContext, BgThreadPoster bgThreadPoster, UiThreadPoster uiThreadPoster) {
        this.appContext = appContext;
        this.bgThreadPoster = bgThreadPoster;
        this.uiThreadPoster = uiThreadPoster;
    }

    public FetchPhotosFromStorageUseCase getFetchLocallyStoredPhotosUseCase() {
        return new FetchPhotosFromStorageUseCase(appContext, bgThreadPoster, uiThreadPoster);
    }
}
