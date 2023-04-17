package com.example.picnic.screens.home.views;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.picnic.R;
import com.example.picnic.common.image.ImageLoader;
import com.example.picnic.common.observable.BaseObservable;
import com.example.picnic.common.utils.ScreenUtils;
import com.example.picnic.screens.homeContent.HomeContentAdapter;
import com.example.picnic.usecases.faceDetection.DetectedFacesData;

import java.util.List;

public class HomeScreenViewMvcImpl extends BaseObservable<HomeScreenViewMvc.Listener> implements HomeScreenViewMvc {

    public HomeScreenViewMvcImpl(LayoutInflater layoutInflater, ImageLoader imageLoader, ScreenUtils screenUtils) {

        rootView = layoutInflater.inflate(R.layout.fragment_home, null, false);

        // find view by ids
        tvGreeting = rootView.findViewById(R.id.tv_greet);
        rvHomeContent = rootView.findViewById(R.id.vp_content);

        homeContentAdapter = new HomeContentAdapter(imageLoader);
        rvHomeContent.setAdapter(homeContentAdapter);
        rvHomeContent.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        boolean isDarkMode = screenUtils.isDarkMode();

        if (isDarkMode)
            rootView.setBackgroundColor(Color.BLACK);

        setGreetingsText();
    }

    private void setGreetingsText() {
        tvGreeting.setText("Good Morning");
    }

    private final View rootView;
    private final TextView tvGreeting;
    private final RecyclerView rvHomeContent;
    private final HomeContentAdapter homeContentAdapter;

    @Override
    public void onPermissionRejected() {

    }

    @Override
    public void bindPhotos(DetectedFacesData detectedFacesData, int pageNo, int offset) {

    }

    @Override
    public void submitAllFetchedImages(List<String> images) {
        homeContentAdapter.submitImages(images);
    }

    @Override
    public void onFetchingPhotos() {
    }

    @Override
    public void onPhotosFetchFailure(String reason) {
    }

    @Override
    public View getRootView() {
        return rootView;
    }
}