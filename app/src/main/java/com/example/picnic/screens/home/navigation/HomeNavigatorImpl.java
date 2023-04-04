package com.example.picnic.screens.home.navigation;

import androidx.fragment.app.FragmentManager;

import com.example.picnic.R;
import com.example.picnic.screens.home.HomeFragment;
import com.example.picnic.screens.imageDetail.ImageDetailFragment;
import com.example.picnic.usecases.faceDetection.DetectedFacesData;

public class HomeNavigatorImpl implements HomeNavigator {

    private final FragmentManager fragmentManager;

    public HomeNavigatorImpl(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void navigateToImageDetailScreen(DetectedFacesData detectedFacesData) {
        fragmentManager.beginTransaction()
                .replace(R.id.main_frag_container, ImageDetailFragment.getInstance(detectedFacesData))
                .addToBackStack(null)
                .commit();
    }
}
