package com.example.picnic.di.application.controllers;

import com.example.picnic.app.PicnicApp;
import com.example.picnic.di.activity.usecases.ActivityUseCaseFactory;
import com.example.picnic.di.application.usecases.UseCaseFactory;
import com.example.picnic.screens.home.controllers.HomeFragmentController;
import com.example.picnic.screens.imageDetail.controller.ImageDetailFragmentController;
import com.example.picnic.usecases.faceDetection.DetectedFacesData;

public class ControllerFactory {

    private final PicnicApp appContext;
    private final UseCaseFactory useCaseFactory;

    public ControllerFactory(PicnicApp appContext, UseCaseFactory useCaseFactory) {
        this.appContext = appContext;
        this.useCaseFactory = useCaseFactory;
    }

    public HomeFragmentController getHomeFragmentController(ActivityUseCaseFactory activityUseCaseFactory) {
        return new HomeFragmentController(
                useCaseFactory.getFetchLocallyStoredPhotosUseCase(),
                activityUseCaseFactory.getRequestPermissionUseCase(),
                useCaseFactory.getFaceDetector(),
                activityUseCaseFactory.getHomeNavigator());
    }

    public ImageDetailFragmentController getImageDetailFragmentController(DetectedFacesData detectedFacesData) {
        return new ImageDetailFragmentController(detectedFacesData);
    }
}
