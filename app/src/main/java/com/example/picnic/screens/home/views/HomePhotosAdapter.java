package com.example.picnic.screens.home.views;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.picnic.R;
import com.example.picnic.common.image.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class HomePhotosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public HomePhotosAdapter(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    private final ImageLoader imageLoader;
    private final List<String> photoUris = new ArrayList<>();


    public class PhotosViewHolder extends RecyclerView.ViewHolder {

        public PhotosViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.iv_photo);
        }

        private final ImageView ivPhoto;

        public void setData(String photoPath) {
            imageLoader.loadFromPath(ivPhoto, photoPath);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo, parent, false);

        return new PhotosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PhotosViewHolder photoHolder = (PhotosViewHolder) holder;
        photoHolder.setData(photoUris.get(position));
    }

    @Override
    public int getItemCount() {
        return photoUris.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void submitData(List<String> newPhotoUris) {
        int last = this.photoUris.size();
        this.photoUris.addAll(newPhotoUris);
        notifyItemRangeInserted(last, newPhotoUris.size());
    }
}
