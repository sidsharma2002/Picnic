package com.example.picnic.screens.home.views.calendar;

import android.content.Context;
import android.os.Build;
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

import com.example.picnic.app.PicnicApp;
import com.example.picnic.common.utils.ScreenUtils;

import java.time.LocalDate;
import java.time.ZoneId;

public class DateHeadingsView extends LinearLayout {

    public DateHeadingsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        vpDates = new RecyclerView(context);
        vpDates.setLayoutParams(new LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT));
        addView(vpDates);

        screenUtils = ((PicnicApp) context.getApplicationContext()).getAppCompositionRoot().getUseCaseFactory().getScreenUtils();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            adapter = new DateHeadingAdapter(screenUtils);
        } else {
            adapter = null; // hehehe
        }
    }

    private final RecyclerView vpDates;
    private final DateHeadingAdapter adapter;
    private final ScreenUtils screenUtils;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        vpDates.setAdapter(adapter);
        vpDates.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(vpDates);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return;

        vpDates.postDelayed(() -> {
            vpDates.smoothScrollToPosition(adapter.getItemCount() / 2 + 2); // jump to middle pos
        }, 700L);
    }
}
