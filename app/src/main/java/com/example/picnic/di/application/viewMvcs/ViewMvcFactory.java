package com.example.picnic.di.application.viewMvcs;

import android.view.LayoutInflater;

import com.example.picnic.screens.home.HomeScreenViewMvc;
import com.example.picnic.screens.home.HomeScreenViewMvcImpl;

public class ViewMvcFactory {

    public HomeScreenViewMvc getHomeScreenViewMvc(LayoutInflater layoutInflater) {
        return new HomeScreenViewMvcImpl(layoutInflater);
    }

}
