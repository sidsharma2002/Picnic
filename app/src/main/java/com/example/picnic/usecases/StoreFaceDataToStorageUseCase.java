package com.example.picnic.usecases;

import android.content.Context;

import androidx.annotation.WorkerThread;

import com.example.picnic.app.PicnicApp;
import com.google.gson.Gson;
import com.google.mlkit.vision.face.Face;

import java.util.List;

public class StoreFaceDataToStorageUseCase {

    private final Context context;
    private final Gson gson;

    public StoreFaceDataToStorageUseCase(PicnicApp picnicApp) {
        gson = new Gson();
        this.context = picnicApp;
    }

    @WorkerThread
    public synchronized void storeSync(String imagePath, List<Face> faces) {

    }

}
