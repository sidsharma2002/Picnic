package com.example.picnic.usecases.faceDetection;

import android.graphics.Bitmap;

import com.google.mlkit.vision.face.Face;

import java.io.Serializable;
import java.util.List;

public class DetectedFacesData implements Serializable {

    private final String imagePath;
    private final List<Face> faceList;

    public DetectedFacesData(String imagePath, List<Face> faceList) {
        this.imagePath = imagePath;
        this.faceList = faceList;
    }

    public String getImagePath() {
        return imagePath;
    }
    public List<Face> getFaceList() {
        return faceList;
    }
}
