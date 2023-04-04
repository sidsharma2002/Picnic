package com.example.picnic.screens.imageDetail.controller;

import com.example.picnic.screens.imageDetail.views.ImageDetailViewMvc;
import com.example.picnic.usecases.faceDetection.DetectedFacesData;

public class ImageDetailFragmentController {

    public ImageDetailFragmentController(DetectedFacesData detectedFacesData) {
        this.detectedFacesData = detectedFacesData;
    }

    private final DetectedFacesData detectedFacesData;
    private ImageDetailViewMvc viewMvc;

    public void bindViewMvc(ImageDetailViewMvc viewMvc) {
        this.viewMvc = viewMvc;
    }

    public void onStart() {

    }

    public void onStop() {

    }
}
