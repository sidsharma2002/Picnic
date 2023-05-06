package com.example.picnic.di.application.usecases;

import com.example.picnic.app.PicnicApp;
import com.example.picnic.common.concurrency.BgThreadPoster;
import com.example.picnic.common.concurrency.UiThreadPoster;
import com.example.picnic.common.utils.ScreenUtils;
import com.example.picnic.screens.homeContent.GetHomeTaskContentUseCase;
import com.example.picnic.usecases.StoreFaceDataToStorageUseCase;
import com.example.picnic.usecases.faceDetection.FaceDetector;
import com.example.picnic.usecases.FetchPhotosFromStorageUseCase;

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

    private StoreFaceDataToStorageUseCase getStoreFaceDataToStorageUseCase() {
        return new StoreFaceDataToStorageUseCase(appContext);
    }

    public FaceDetector getFaceDetector() {
        return new FaceDetector(appContext, getStoreFaceDataToStorageUseCase());
    }

    public GetHomeTaskContentUseCase getHomeTaskContentUseCase() {
        return new GetHomeTaskContentUseCase();
    }

    public ScreenUtils getScreenUtils() {
        return new ScreenUtils(appContext);
    }
}
