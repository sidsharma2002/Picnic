package com.example.picnic.screens.home;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;

import com.example.picnic.R;

import java.util.List;

public class HomeScreenViewMvcImpl implements HomeScreenViewMvc {

    public HomeScreenViewMvcImpl(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
        rootView = layoutInflater.inflate(R.layout.fragment_home, null, false);
    }

    private LayoutInflater layoutInflater;
    private View rootView;

    @Override
    public void onPermissionRejected() {

    }

    @Override
    public void bindPhotos(List<Uri> images, int pageNo, int offset) {

    }

    @Override
    public void onFetchingPhotos() {

    }

    @Override
    public void onPhotosFetchFailure(String reason) {

    }

    @Override
    public View getRootView() {
        return rootView;
    }
}