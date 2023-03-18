package com.example.picnic.screens.home.views;

import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.picnic.R;
import com.example.picnic.common.imageLoader.ImageLoader;

import java.util.List;

public class HomeScreenViewMvcImpl implements HomeScreenViewMvc {

    public HomeScreenViewMvcImpl(LayoutInflater layoutInflater, ImageLoader imageLoader) {
        rootView = layoutInflater.inflate(R.layout.fragment_home, null, false);

        // setup rv
        rvPhotos = rootView.findViewById(R.id.rv_photos);
        homePhotosAdapter = new HomePhotosAdapter(imageLoader);
        rvPhotos.setAdapter(homePhotosAdapter);
        StaggeredGridLayoutManager lm = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        lm.setItemPrefetchEnabled(true);
        rvPhotos.setLayoutManager(lm);
    }

    private final View rootView;
    private HomePhotosAdapter homePhotosAdapter;
    private RecyclerView rvPhotos;

    @Override
    public void onPermissionRejected() {

    }

    @Override
    public void bindPhotos(List<String> images, int pageNo, int offset) {
        homePhotosAdapter.submitData(images);
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