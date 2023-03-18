package com.example.picnic.screens.home.controllers;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import com.example.picnic.common.observable.ObservableDataHolder;
import com.example.picnic.common.observable.ObservableDataHolder.Observer;
import com.example.picnic.common.permissions.RequestPermissionUseCase;
import com.example.picnic.common.permissions.RequestPermissionUseCase.Listener;
import com.example.picnic.screens.home.usecases.FetchPhotosFromStorageUseCase;
import com.example.picnic.screens.home.views.HomeScreenViewMvc;

import java.util.List;

public class HomeFragmentController {

    public HomeFragmentController(FetchPhotosFromStorageUseCase fetchPhotosUseCase,
                                  RequestPermissionUseCase requestPermissionUseCase) {
        fetchPhotosFromStorageUseCase = fetchPhotosUseCase;
        this.requestPermissionUseCase = requestPermissionUseCase;
    }

    private HomeScreenViewMvc viewMvc;

    // use-cases
    private final FetchPhotosFromStorageUseCase fetchPhotosFromStorageUseCase;
    private final RequestPermissionUseCase requestPermissionUseCase;


    // -----------------------
    // Lifecycle Handling

    public void onViewCreated() {
        requestExternalStoragePermission();
    }

    public void onStart() {
        fetchPhotosFromStorageUseCase.obsImagePaths.register(imagesObserver);
    }

    public void onStop() {
        fetchPhotosFromStorageUseCase.obsImagePaths.unregister(imagesObserver);
    }

    // Lifecycle Handling
    // -----------------------


    private void requestExternalStoragePermission() {
        requestPermissionUseCase.request(READ_EXTERNAL_STORAGE, new Listener() {
            @Override
            public void onPermissionGranted() {
                viewMvc.onFetchingPhotos();
                fetchPhotosFromStorageUseCase.executeAsync();
            }

            @Override
            public void onPermissionRejected() {
                viewMvc.onPermissionRejected();
            }
        });
    }


    // --------------------------
    // Observers

    private final Observer<List<String>> imagesObserver = new Observer<List<String>>() {
        @Override
        public void onDataChanged(List<String> data) {
            viewMvc.bindPhotos(data, 0, 0);
        }
    };

    // Observers
    // --------------------------

    public void bindViewMvc(HomeScreenViewMvc homeScreenViewMvc) {
        viewMvc = homeScreenViewMvc;
    }
}
