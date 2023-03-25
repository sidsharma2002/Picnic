package com.example.picnic.screens.home.controllers;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Pair;

import com.example.picnic.common.observable.ObservableDataHolder;
import com.example.picnic.common.observable.ObservableDataHolder.Observer;
import com.example.picnic.common.permissions.RequestPermissionUseCase;
import com.example.picnic.common.permissions.RequestPermissionUseCase.Listener;
import com.example.picnic.faceDetection.FaceDetector;
import com.example.picnic.screens.home.usecases.FetchPhotosFromStorageUseCase;
import com.example.picnic.screens.home.views.HomeScreenViewMvc;
import com.google.mlkit.vision.face.Face;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class HomeFragmentController {

    public HomeFragmentController(FetchPhotosFromStorageUseCase fetchPhotosUseCase,
                                  RequestPermissionUseCase requestPermissionUseCase,
                                  FaceDetector faceDetector) {
        fetchPhotosFromStorageUseCase = fetchPhotosUseCase;
        this.requestPermissionUseCase = requestPermissionUseCase;
        this.faceDetector = faceDetector;
    }

    private HomeScreenViewMvc viewMvc;

    // use-cases
    private final FetchPhotosFromStorageUseCase fetchPhotosFromStorageUseCase;
    private final RequestPermissionUseCase requestPermissionUseCase;
    private final FaceDetector faceDetector;


    // -----------------------
    // Lifecycle Handling

    public void onViewCreated() {
        requestExternalStoragePermission();
    }

    public void onStart() {
        fetchPhotosFromStorageUseCase.obsImagePaths.register(imagesObserver);
        faceDetector.obsFaces.register(faceResultObserver);
    }

    public void onStop() {
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

    private final Observer<List<String>> imagesObserver = new Observer<List<String>>() {
        @Override
        public void onDataChanged(List<String> data) {

            int batch = 1;

            for (String path : data) {
                faceDetector.detectFaceAndNotify(path);

                if (++batch > 100)
                    break;
            }

            // viewMvc.bindPhotos(data, 0, 0);
        }
    };

    private final Observer<String> faceResultObserver = data -> {
        viewMvc.bindPhotos(Collections.singletonList(data), 0, 0);
    };

    // Observers
    // --------------------------

    public void bindViewMvc(HomeScreenViewMvc homeScreenViewMvc) {
        viewMvc = homeScreenViewMvc;
    }
}
