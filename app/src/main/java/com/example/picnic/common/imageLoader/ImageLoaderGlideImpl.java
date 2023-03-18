package com.example.picnic.common.imageLoader;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.loader.content.CursorLoader;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.InputStream;

public class ImageLoaderGlideImpl implements ImageLoader {

    @Override
    public void loadFromPath(ImageView ivPhoto, String photoPath) {
        File file = new File(photoPath);
        Glide.with(ivPhoto.getContext()).load(file).into(ivPhoto);
    }
}
