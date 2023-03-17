package com.example.picnic.screens.home;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import android.net.Uri;

import com.example.picnic.common.permissions.RequestPermissionUseCase;
import com.example.picnic.common.permissions.RequestPermissionUseCase.Listener;

import java.util.List;

public class HomeFragmentController {

    public HomeFragmentController(FetchLocallyStoredPhotosUseCase fetchPhotosUseCase,
                                  RequestPermissionUseCase requestPermissionUseCase) {
        fetchLocallyStoredPhotosUseCase = fetchPhotosUseCase;
        this.requestPermissionUseCase = requestPermissionUseCase;
    }

    private HomeScreenViewMvc viewMvc;

    // use-cases
    private FetchLocallyStoredPhotosUseCase fetchLocallyStoredPhotosUseCase;
    private RequestPermissionUseCase requestPermissionUseCase;


    // -----------------------
    // Lifecycle Handling

    void onViewCreated() {
        requestExternalStoragePermission();
    }

    private void requestExternalStoragePermission() {
        requestPermissionUseCase.request(READ_EXTERNAL_STORAGE, new Listener() {
            @Override
            public void onPermissionGranted() {
                viewMvc.onFetchingPhotos();
                fetchLocallyStoredPhotosUseCase.execute();
            }

            @Override
            public void onPermissionRejected() {
                viewMvc.onPermissionRejected();
            }
        });
    }

    void onStart() {
        fetchLocallyStoredPhotosUseCase.register(fetchPhotosListener);
    }

    void onStop() {
        fetchLocallyStoredPhotosUseCase.unregister(fetchPhotosListener);
    }

    // Lifecycle Handling
    // -----------------------


    // --------------------------
    // Observers

    private final FetchLocallyStoredPhotosUseCase.Listener fetchPhotosListener = new FetchLocallyStoredPhotosUseCase.Listener() {
        @Override
        public void onImagesFetched(List<Uri> images, int pageNo, int offset) {
            viewMvc.bindPhotos(images, pageNo, offset);
        }

        @Override
        public void onFailure(String reason) {
            viewMvc.onPhotosFetchFailure(reason);
        }
    };

    // Observers
    // --------------------------

    void bindViewMvc(HomeScreenViewMvc homeScreenViewMvc) {
        viewMvc = homeScreenViewMvc;
    }
}
