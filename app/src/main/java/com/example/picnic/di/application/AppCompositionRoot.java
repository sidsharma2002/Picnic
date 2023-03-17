package com.example.picnic.di.application;

import androidx.appcompat.app.AppCompatActivity;

import com.example.picnic.app.PicnicApp;
import com.example.picnic.di.activity.usecases.ActivityUseCaseFactory;
import com.example.picnic.di.application.controllers.ControllerFactory;
import com.example.picnic.di.application.usecases.UseCaseFactory;
import com.example.picnic.di.application.viewMvcs.ViewMvcFactory;

public class AppCompositionRoot {

    private PicnicApp appContext;

    private ViewMvcFactory viewMvcFactory;
    private ControllerFactory controllerFactory;
    private UseCaseFactory useCaseFactory;

    public AppCompositionRoot(PicnicApp appContext) {

        this.appContext = appContext;

        viewMvcFactory = new ViewMvcFactory();
        useCaseFactory = new UseCaseFactory(appContext);
        controllerFactory = new ControllerFactory(appContext, useCaseFactory);
    }

    public ViewMvcFactory getViewMvcFactory() {
        return viewMvcFactory;
    }

    public ControllerFactory getControllerFactory() {
        return controllerFactory;
    }

    public ActivityUseCaseFactory getActivityUseCaseFactory(AppCompatActivity activity) {
        return new ActivityUseCaseFactory(activity); // don't keep reference of this.
    }

}
