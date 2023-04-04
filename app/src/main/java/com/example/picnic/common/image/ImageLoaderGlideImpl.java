package com.example.picnic.common.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.picnic.app.PicnicApp;

import java.io.File;

public class ImageLoaderGlideImpl implements ImageLoader {

    @Override
    public void loadFromPath(ImageView ivPhoto, String photoPath) {
        File file = new File(photoPath);
        Glide.with(ivPhoto.getContext()).load(file).into(ivPhoto);
    }

    @Override
    public Bitmap getBitmapFromPathSync(PicnicApp context, String photoPath) {
        try {
            File file = new File(photoPath);
            return Glide.with(context).asBitmap().load(file).into(-1, -1).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
