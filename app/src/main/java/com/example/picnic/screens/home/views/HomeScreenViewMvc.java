package com.example.picnic.screens.home.views;

import android.net.Uri;
import android.view.View;

import java.util.List;

public interface HomeScreenViewMvc {
    void onPermissionRejected();

    void bindPhotos(List<String> images, int pageNo, int offset);
    void onFetchingPhotos();
    void onPhotosFetchFailure(String reason);

    View getRootView();
}
