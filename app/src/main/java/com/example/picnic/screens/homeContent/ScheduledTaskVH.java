package com.example.picnic.screens.homeContent;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.picnic.R;
import com.example.picnic.common.image.ImageLoader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ScheduledTaskVH extends RecyclerView.ViewHolder {

    public ScheduledTaskVH(@NonNull View itemView, ImageLoader imageLoader) {
        super(itemView);
        this.imageLoader = imageLoader;
        iv1 = itemView.findViewById(R.id.iv1);
        iv2 = itemView.findViewById(R.id.iv2);
        iv3 = itemView.findViewById(R.id.iv3);
        tv2 = itemView.findViewById(R.id.tv2);
        svImagesParent = itemView.findViewById(R.id.images_main_parent);

        svImagesParent.setHorizontalScrollBarEnabled(false);
    }

    static public ScheduledTaskVH getInstance(ViewGroup parent, ImageLoader imageLoader) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scheduled_task, parent, false);
        return new ScheduledTaskVH(view, imageLoader);
    }

    private final ImageLoader imageLoader;

    private final TextView tv2;

    private final ImageView iv1;
    private final ImageView iv2;
    private final ImageView iv3;
    private final HorizontalScrollView svImagesParent;

    public void bindData(List<String> allImages, ScheduledTaskData data) {
        if (allImages.size() < 3 || allImages.size() - 1 < getAdapterPosition()) {
            svImagesParent.setVisibility(View.GONE);
            return;
        }

        svImagesParent.setVisibility(View.VISIBLE);
        imageLoader.loadFromPath(iv1, allImages.get(getAdapterPosition()));

        if (getAdapterPosition() - 1 >= 0) {
            imageLoader.loadFromPath(iv2, allImages.get(getAdapterPosition() - 1));
            iv2.setVisibility(View.VISIBLE);
        } else
            iv2.setVisibility(View.GONE);

        if (getAdapterPosition() - 2 >= 0) {
            imageLoader.loadFromPath(iv3, allImages.get(getAdapterPosition() - 2));
            iv3.setVisibility(View.VISIBLE);
        } else
            iv3.setVisibility(View.GONE);
    }

}
