package com.example.picnic.screens.home.navigation;

import com.example.picnic.usecases.faceDetection.DetectedFacesData;

public interface HomeNavigator {
    void navigateToImageDetailScreen(DetectedFacesData detectedFacesData);
}
