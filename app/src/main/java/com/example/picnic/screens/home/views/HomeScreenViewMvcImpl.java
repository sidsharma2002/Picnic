package com.example.picnic.screens.home.views;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.picnic.R;
import com.example.picnic.common.image.ImageLoader;
import com.example.picnic.common.observable.BaseObservable;
import com.example.picnic.common.utils.ScreenUtils;
import com.example.picnic.screens.home.views.calendar.DateHeadingsView;
import com.example.picnic.screens.home.views.homeViewpager.HomeVpAdapter;
import com.example.picnic.screens.homeContent.HomeContentAdapter;
import com.example.picnic.screens.homeContent.ScheduledTaskData;
import com.example.picnic.usecases.faceDetection.DetectedFacesData;

import java.util.List;

public class HomeScreenViewMvcImpl extends BaseObservable<HomeScreenViewMvc.Listener> implements HomeScreenViewMvc {

    public HomeScreenViewMvcImpl(LayoutInflater layoutInflater, ImageLoader imageLoader, ScreenUtils screenUtils) {

        rootView = layoutInflater.inflate(R.layout.fragment_home, null, false);

        // find view by ids
        tvGreeting = rootView.findViewById(R.id.tv_greet);

        vpHomeContent = rootView.findViewById(R.id.vp_content);
        homeVpAdapter = new HomeVpAdapter(imageLoader);
        vpHomeContent.setAdapter(homeVpAdapter);
        vpHomeContent.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        vpHomeContent.setCurrentItem(homeVpAdapter.getItemCount() / 2, true);

        DateHeadingsView dateHeadingsView = rootView.findViewById(R.id.rv_date_heading);
        vpHomeContent.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    dateHeadingsView.notifyCurrentlySelectedItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

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
    private final ViewPager2 vpHomeContent;
    private final HomeVpAdapter homeVpAdapter;

    @Override
    public void onPermissionRejected() {

    }

    @Override
    public void bindPhotos(DetectedFacesData detectedFacesData, int pageNo, int offset) {

    }

    @Override
    public void bindData(List<ScheduledTaskData> dataList) {
        homeVpAdapter.submitHomeContent(dataList);
    }

    @Override
    public void submitAllFetchedImages(List<String> images) {
        homeVpAdapter.submitImages(images);
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