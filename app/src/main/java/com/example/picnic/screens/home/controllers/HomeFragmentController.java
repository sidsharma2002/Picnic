package com.example.picnic.screens.home.controllers;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import com.example.picnic.common.observable.ObservableDataHolder.Observer;
import com.example.picnic.common.permissions.RequestPermissionUseCase;
import com.example.picnic.common.permissions.RequestPermissionUseCase.Listener;
import com.example.picnic.screens.home.navigation.HomeNavigator;
import com.example.picnic.usecases.faceDetection.DetectedFacesData;
import com.example.picnic.usecases.faceDetection.FaceDetector;
import com.example.picnic.usecases.FetchPhotosFromStorageUseCase;
import com.example.picnic.screens.home.views.HomeScreenViewMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragmentController {

    public HomeFragmentController(FetchPhotosFromStorageUseCase fetchPhotosUseCase,
                                  RequestPermissionUseCase requestPermissionUseCase,
                                  FaceDetector faceDetector,
                                  HomeNavigator homeNavigator) {
        fetchPhotosFromStorageUseCase = fetchPhotosUseCase;
        this.requestPermissionUseCase = requestPermissionUseCase;
        this.faceDetector = faceDetector;
        this.homeNavigator = homeNavigator;
    }

    private HomeScreenViewMvc viewMvc;
    private HomeNavigator homeNavigator;

    // use-cases
    private final FetchPhotosFromStorageUseCase fetchPhotosFromStorageUseCase;
    private final RequestPermissionUseCase requestPermissionUseCase;
    private final FaceDetector faceDetector;

    private boolean isDetectorBusy = false;


    // -----------------------
    // Lifecycle Handling

    public void onViewCreated() {
        requestExternalStoragePermission();
    }

    public void onStart() {
        viewMvc.register(viewMvcListener);
        fetchPhotosFromStorageUseCase.obsImagePaths.register(imagesObserver);
        faceDetector.obsFaces.register(faceResultObserver);
    }

    public void onStop() {
        viewMvc.unregister(viewMvcListener);
        fetchPhotosFromStorageUseCase.obsImagePaths.unregister(imagesObserver);
        faceDetector.obsFaces.unregister(faceResultObserver);
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

    private final HomeScreenViewMvc.Listener viewMvcListener = new HomeScreenViewMvc.Listener() {
        @Override
        public void onImageClicked(int position, DetectedFacesData detectedFacesData) {
            homeNavigator.navigateToImageDetailScreen(detectedFacesData);
        }
    };

    private final Observer<List<String>> imagesObserver = new Observer<List<String>>() {
        @Override
        public void onDataChanged(List<String> data) {
            if (isDetectorBusy) return;
            isDetectorBusy = true;

            int i = 0;
            for (String path : data) {
                faceDetector.detectFaceAndNotify(path);

                if (++i > 100)
                    return;
            }
        }
    };

    private final Observer<DetectedFacesData> faceResultObserver = data -> {
        viewMvc.bindPhotos(data, 0, 0);
    };

    // Observers
    // --------------------------

    public void bindViewMvc(HomeScreenViewMvc homeScreenViewMvc) {
        viewMvc = homeScreenViewMvc;
    }
}
