package com.example.picnic.screens.home;

import android.net.Uri;
import android.view.View;

import java.util.List;

public interface HomeScreenViewMvc {
    void onPermissionRejected();

    void bindPhotos(List<Uri> images, int pageNo, int offset);
    void onFetchingPhotos();
    void onPhotosFetchFailure(String reason);

    View getRootView();
}
