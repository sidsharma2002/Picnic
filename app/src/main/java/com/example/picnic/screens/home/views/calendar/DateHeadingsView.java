package com.example.picnic.screens.home.views.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager2.widget.ViewPager2;

public class DateHeadingsView extends LinearLayout {

    public DateHeadingsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        vpDates = new RecyclerView(context);
        vpDates.setLayoutParams(new LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT));
        addView(vpDates);

        adapter = new DateHeadingAdapter();
    }

    private final RecyclerView vpDates;
    private final DateHeadingAdapter adapter;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        vpDates.setAdapter(adapter);
        vpDates.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(vpDates);

        vpDates.postDelayed(() -> {
            vpDates.smoothScrollToPosition(6 + 2);
        }, 300L);
    }
}
