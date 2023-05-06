package com.example.picnic.screens.home.views.homeViewpager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.picnic.R;
import com.example.picnic.common.image.ImageLoader;
import com.example.picnic.screens.homeContent.HomeContentAdapter;
import com.example.picnic.screens.homeContent.ScheduledTaskData;

import java.util.ArrayList;
import java.util.List;

public class HomeVpAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    static class MainViewHolder extends RecyclerView.ViewHolder {

        static public MainViewHolder getInstance(ViewGroup parent, ImageLoader imageLoader) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vp_home, parent, false);
            return new MainViewHolder(view, imageLoader);
        }

        public MainViewHolder(@NonNull View itemView, ImageLoader imageLoader) {
            super(itemView);
            this.imageLoader = imageLoader;
            rvHomeContent = itemView.findViewById(R.id.rv_home_content);
        }

        private final ImageLoader imageLoader;
        private final RecyclerView rvHomeContent;

        public void setData(List<String> allImages, List<ScheduledTaskData> dataList) {
            HomeContentAdapter homeContentAdapter = new HomeContentAdapter(imageLoader);
            homeContentAdapter.submitImages(allImages, dataList);
            rvHomeContent.setAdapter(homeContentAdapter);
            rvHomeContent.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }

    }

    public HomeVpAdapter(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    private final ImageLoader imageLoader;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return MainViewHolder.getInstance(parent, imageLoader);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MainViewHolder) {
            ((MainViewHolder) holder).setData(allImages, dataList);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private List<String> allImages = new ArrayList<>();
    private List<ScheduledTaskData> dataList = new ArrayList<>();

    private int dataSubmittedCount = 0;

    public void submitImages(List<String> images) {
        allImages = images;
        dataSubmittedCount++;
    }

    public void submitHomeContent(List<ScheduledTaskData> dataList) {
        this.dataList = dataList;
        dataSubmittedCount++;
    }
}
