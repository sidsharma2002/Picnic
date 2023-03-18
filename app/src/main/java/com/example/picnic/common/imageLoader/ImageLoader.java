package com.example.picnic.common.imageLoader;

import android.net.Uri;
import android.widget.ImageView;

public interface ImageLoader {

    public void load(ImageView ivPhoto, String photoPath);
}
