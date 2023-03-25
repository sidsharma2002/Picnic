package com.example.picnic.faceRecognition.recognizer;

import android.graphics.Bitmap;

import androidx.annotation.WorkerThread;

public interface FaceRecognizer {

    @WorkerThread
    Float[] getFaceEmbeddingsSync(Bitmap image);
}
