package com.example.picnic.usecases.faceRecognition.recognizer;

import android.graphics.Bitmap;

import androidx.annotation.WorkerThread;

public interface FaceRecognizer {

    @WorkerThread
    Float[] getFaceEmbeddingsSync(Bitmap image);
}
