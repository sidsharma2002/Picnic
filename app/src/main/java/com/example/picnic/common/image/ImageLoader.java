package com.example.picnic.common.image;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.WorkerThread;

import com.example.picnic.app.PicnicApp;

public interface ImageLoader {

    void loadFromPath(ImageView ivPhoto, String photoPath);

    @WorkerThread
    Bitmap getBitmapFromPathSync(PicnicApp context, String photoPath);
}
