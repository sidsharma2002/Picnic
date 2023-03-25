package com.example.picnic.screens.home.views;

import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.picnic.R;
import com.example.picnic.common.image.ImageLoader;

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

        loader = rootView.findViewById(R.id.loader);
    }

    private final View rootView;
    private final View loader;
    private final HomePhotosAdapter homePhotosAdapter;
    private final RecyclerView rvPhotos;

    @Override
    public void onPermissionRejected() {

    }

    @Override
    public void bindPhotos(List<String> images, int pageNo, int offset) {
        loader.setVisibility(View.GONE);
        homePhotosAdapter.submitData(images);
    }

    @Override
    public void onFetchingPhotos() {
        loader.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPhotosFetchFailure(String reason) {
        loader.setVisibility(View.GONE);
    }

    @Override
    public View getRootView() {
        return rootView;
    }
}