package com.example.picnic.screens.home.controllers;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import android.net.Uri;
import android.util.Log;

import com.example.picnic.common.permissions.RequestPermissionUseCase;
import com.example.picnic.common.permissions.RequestPermissionUseCase.Listener;
import com.example.picnic.screens.home.usecases.FetchLocallyStoredPhotosUseCase;
import com.example.picnic.screens.home.views.HomeScreenViewMvc;

import java.util.List;

public class HomeFragmentController {

    public HomeFragmentController(FetchLocallyStoredPhotosUseCase fetchPhotosUseCase,
                                  RequestPermissionUseCase requestPermissionUseCase) {
        fetchLocallyStoredPhotosUseCase = fetchPhotosUseCase;
        this.requestPermissionUseCase = requestPermissionUseCase;
    }

    private HomeScreenViewMvc viewMvc;

    // use-cases
    private final FetchLocallyStoredPhotosUseCase fetchLocallyStoredPhotosUseCase;
    private final RequestPermissionUseCase requestPermissionUseCase;


    // -----------------------
    // Lifecycle Handling

    public void onViewCreated() {
        requestExternalStoragePermission();
    }

    public void onStart() {
        fetchLocallyStoredPhotosUseCase.register(fetchPhotosListener);
    }

    public void onStop() {
        fetchLocallyStoredPhotosUseCase.unregister(fetchPhotosListener);
    }

    // Lifecycle Handling
    // -----------------------


    private void requestExternalStoragePermission() {
        requestPermissionUseCase.request(READ_EXTERNAL_STORAGE, new Listener() {
            @Override
            public void onPermissionGranted() {
                viewMvc.onFetchingPhotos();
                fetchLocallyStoredPhotosUseCase.executeAsync();
            }

            @Override
            public void onPermissionRejected() {
                viewMvc.onPermissionRejected();
            }
        });
    }


    // --------------------------
    // Observers

    private final FetchLocallyStoredPhotosUseCase.Listener fetchPhotosListener = new FetchLocallyStoredPhotosUseCase.Listener() {
        @Override
        public void onImagesFetched(List<String> images, int pageNo, int offset) {
            Log.d("images", "size : " + images.size());
            viewMvc.bindPhotos(images, pageNo, offset);
        }

        @Override
        public void onFailure(String reason) {
            viewMvc.onPhotosFetchFailure(reason);
        }
    };

    // Observers
    // --------------------------

    public void bindViewMvc(HomeScreenViewMvc homeScreenViewMvc) {
        viewMvc = homeScreenViewMvc;
    }
}
