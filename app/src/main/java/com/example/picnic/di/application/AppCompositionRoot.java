package com.example.picnic.di.application;

import androidx.appcompat.app.AppCompatActivity;

import com.example.picnic.app.PicnicApp;
import com.example.picnic.common.concurrency.BgThreadPoster;
import com.example.picnic.common.concurrency.UiThreadPoster;
import com.example.picnic.common.image.ImageLoader;
import com.example.picnic.common.image.ImageLoaderGlideImpl;
import com.example.picnic.di.activity.usecases.ActivityUseCaseFactory;
import com.example.picnic.di.application.controllers.ControllerFactory;
import com.example.picnic.di.application.usecases.UseCaseFactory;
import com.example.picnic.di.application.viewMvcs.ViewMvcFactory;

public class AppCompositionRoot {

    private PicnicApp appContext;

    private ViewMvcFactory viewMvcFactory;
    private ControllerFactory controllerFactory;
    private UseCaseFactory useCaseFactory;
    private BgThreadPoster bgThreadPoster;
    private UiThreadPoster uiThreadPoster;
    private ImageLoader imageLoader;

    public AppCompositionRoot(PicnicApp appContext) {

        this.appContext = appContext;

        this.bgThreadPoster = new BgThreadPoster();
        this.uiThreadPoster = new UiThreadPoster();

        this.imageLoader = new ImageLoaderGlideImpl();

        viewMvcFactory = new ViewMvcFactory(imageLoader);
        useCaseFactory = new UseCaseFactory(appContext, bgThreadPoster, uiThreadPoster);
        controllerFactory = new ControllerFactory(appContext, useCaseFactory);
    }

    public ViewMvcFactory getViewMvcFactory() {
        return viewMvcFactory;
    }

    public ControllerFactory getControllerFactory() {
        return controllerFactory;
    }

    public ActivityUseCaseFactory getNewActivityUseCaseFactory(AppCompatActivity activity) {
        return new ActivityUseCaseFactory(activity); // don't keep reference of this.
    }

}
