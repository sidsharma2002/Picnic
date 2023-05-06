package com.example.picnic.screens.home.views;

import android.view.View;

import com.example.picnic.common.observable.Observable;
import com.example.picnic.screens.homeContent.ScheduledTaskData;
import com.example.picnic.usecases.faceDetection.DetectedFacesData;

import java.util.List;

public interface HomeScreenViewMvc extends Observable<HomeScreenViewMvc.Listener> {

    interface Listener {
        void onImageClicked(int position, DetectedFacesData detectedFacesData);
    }

    void onPermissionRejected();

    void bindPhotos(DetectedFacesData detectedFacesData, int pageNo, int offset);
    void bindData(List<ScheduledTaskData> dataList);
    void submitAllFetchedImages(List<String> images);
    void onFetchingPhotos();
    void onPhotosFetchFailure(String reason);

    View getRootView();
}
