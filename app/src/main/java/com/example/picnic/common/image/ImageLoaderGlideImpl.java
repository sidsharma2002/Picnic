package com.example.picnic.common.image;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

public class ImageLoaderGlideImpl implements ImageLoader {

    @Override
    public void loadFromPath(ImageView ivPhoto, String photoPath) {
        File file = new File(photoPath);
        Glide.with(ivPhoto.getContext()).load(file).into(ivPhoto);
    }
}
