package com.example.picnic.di.application.viewMvcs;

import android.view.LayoutInflater;

import com.example.picnic.common.image.ImageLoader;
import com.example.picnic.screens.home.views.HomeScreenViewMvc;
import com.example.picnic.screens.home.views.HomeScreenViewMvcImpl;

public class ViewMvcFactory {

    private ImageLoader imageLoader;

    public ViewMvcFactory(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    public HomeScreenViewMvc getHomeScreenViewMvc(LayoutInflater layoutInflater) {
        return new HomeScreenViewMvcImpl(layoutInflater, imageLoader);
    }

}
