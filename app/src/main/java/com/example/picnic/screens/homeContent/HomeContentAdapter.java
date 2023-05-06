package com.example.picnic.screens.homeContent;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.picnic.common.image.ImageLoader;
import com.example.picnic.di.application.usecases.UseCaseFactory;

import java.util.ArrayList;
import java.util.List;

public class HomeContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public HomeContentAdapter(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    private final ImageLoader imageLoader;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ScheduledTaskVH.getInstance(parent, imageLoader);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ScheduledTaskVH) {
            ((ScheduledTaskVH) holder).bindData(allImages, dataList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    // data lists
    private List<String> allImages = new ArrayList<>();
    private List<ScheduledTaskData> dataList = new ArrayList<>();

    public void submitImages(List<String> images, List<ScheduledTaskData> dataList) {
        allImages = images;
        this.dataList = dataList;
        notifyDataSetChanged();
    }
}
