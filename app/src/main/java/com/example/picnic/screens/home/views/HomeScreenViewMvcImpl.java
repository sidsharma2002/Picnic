package com.example.picnic.screens.home.views;

import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.picnic.R;
import com.example.picnic.common.image.ImageLoader;
import com.example.picnic.common.observable.BaseObservable;
import com.example.picnic.usecases.faceDetection.DetectedFacesData;

public class HomeScreenViewMvcImpl extends BaseObservable<HomeScreenViewMvc.Listener> implements HomeScreenViewMvc {

    public HomeScreenViewMvcImpl(LayoutInflater layoutInflater, ImageLoader imageLoader) {
        rootView = layoutInflater.inflate(R.layout.fragment_home, null, false);

        // setup rv
        rvPhotos = rootView.findViewById(R.id.rv_photos);

        homePhotosAdapter = new HomePhotosAdapter(imageLoader);
        homePhotosAdapter.bindListener((position, detectedFacesData) -> {
            for (Listener listener : getObservers()) {
                listener.onImageClicked(position, detectedFacesData);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rootView.getContext());
        rvPhotos.setLayoutManager(linearLayoutManager);

        rvPhotos.setAdapter(homePhotosAdapter);
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
    public void bindPhotos(DetectedFacesData detectedFacesData, int pageNo, int offset) {
        loader.setVisibility(View.GONE);
        homePhotosAdapter.submitData(detectedFacesData);
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