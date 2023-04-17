package com.example.picnic.di.application.viewMvcs;

import android.view.LayoutInflater;

import com.example.picnic.common.image.ImageLoader;
import com.example.picnic.common.utils.ScreenUtils;
import com.example.picnic.di.application.usecases.UseCaseFactory;
import com.example.picnic.screens.home.views.HomeScreenViewMvc;
import com.example.picnic.screens.home.views.HomeScreenViewMvcImpl;
import com.example.picnic.screens.imageDetail.views.ImageDetailViewMvc;
import com.example.picnic.screens.imageDetail.views.ImageDetailViewMvcImpl;
import com.example.picnic.usecases.faceDetection.DetectedFacesData;

public class ViewMvcFactory {

    private final ImageLoader imageLoader;
    private final UseCaseFactory useCaseFactory;

    public ViewMvcFactory(ImageLoader imageLoader, UseCaseFactory useCaseFactory) {
        this.imageLoader = imageLoader;
        this.useCaseFactory = useCaseFactory;
    }

    public HomeScreenViewMvc getHomeScreenViewMvc(LayoutInflater layoutInflater) {
        return new HomeScreenViewMvcImpl(layoutInflater, imageLoader, useCaseFactory.getScreenUtils());
    }

    public ImageDetailViewMvc getImageDetailViewMvc(LayoutInflater layoutInflater, DetectedFacesData detectedFacesData) {
        return new ImageDetailViewMvcImpl(layoutInflater, imageLoader, useCaseFactory.getScreenUtils(), detectedFacesData);
    }

}
