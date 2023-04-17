package com.example.picnic.screens.home.views.calendar;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.picnic.R;
import com.example.picnic.common.utils.ScreenUtils;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DateHeadingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ScreenUtils screenUtils;

    public DateHeadingAdapter(ScreenUtils screenUtils) {
        this.screenUtils = screenUtils;
    }

    // viewholder

    static class DateViewHolder extends RecyclerView.ViewHolder {

        public DateViewHolder(@NonNull View itemView, ScreenUtils screenUtils) {
            super(itemView);
            context = itemView.getContext();
            tvDay = itemView.findViewById(R.id.tv_day);
            tvDate = itemView.findViewById(R.id.tv_date);
            parent = itemView.findViewById(R.id.cv_dates_heading);
            view = itemView.findViewById(R.id.ninja);
            this.screenUtils = screenUtils;
        }

        static public DateViewHolder getInstance(ViewGroup parent, ScreenUtils screenUtils) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dates_heading, parent, false);
            return new DateViewHolder(view, screenUtils);
        }

        private final CardView parent;
        private final TextView tvDay;
        private final TextView tvDate;
        private final View view;
        private final Context context;
        private final ScreenUtils screenUtils;

        public void setData(Boolean isToday, String day, String date) {
            Log.d("CALENDAR", "day text setData " + day);

            if (isToday) {
                parent.setCardBackgroundColor(context.getColor(R.color.purple_500)); // couldn't find reliable way to get themed color like colorPrimary.
                tvDay.setTextColor(context.getColor(R.color.white));
                //tvDay.setTextSize(16);

                tvDate.setTextColor(context.getColor(R.color.white));
                //tvDate.setTextSize(24);
            } else {
                //tvDay.setTextSize(12);
                //tvDate.setTextSize(18);

                view.setVisibility(View.GONE);

                int color = R.color.black;

                if (screenUtils.isDarkMode())
                    color = R.color.white;

                tvDay.setTextColor(context.getColor(color));
                tvDate.setTextColor(context.getColor(color));
                parent.setCardElevation(0f);

                color = R.color.white;

                if (screenUtils.isDarkMode())
                    color = R.color.black;

                parent.setCardBackgroundColor(context.getColor(color));
            }

            tvDay.setText(day.substring(0, 3));
            tvDate.setText(date);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return DateViewHolder.getInstance(parent, screenUtils);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        // out of x, today's day should be in the middle i.e pos = x/2
        int todaysDayPos = getItemCount() / 2;

        final LocalDate currentLocalDate = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        final int day = currentLocalDate.getDayOfMonth();
        final DayOfWeek dayOfWeek = LocalDate.of(currentLocalDate.getYear(), currentLocalDate.getMonth(), day).getDayOfWeek();

        if (position == todaysDayPos) {
            ((DateViewHolder) holder).setData(
                    true,
                    dayOfWeek.toString(), String.valueOf(day));
            return;
        }

        // find diff and get (today + diff) local date.
        if (position > todaysDayPos) {

            int diff = position - todaysDayPos;
            LocalDate currentPosDayLD = currentLocalDate.plus(Period.ofDays(diff));

            ((DateViewHolder) holder).setData(
                    false,
                    currentPosDayLD.getDayOfWeek().toString(), String.valueOf(currentPosDayLD.getDayOfMonth()));

            return;
        }

        // find diff and get (today - diff) local date.
        int diff = todaysDayPos - position;
        LocalDate currentPosDayLD = currentLocalDate.minus(Period.ofDays(diff));

        ((DateViewHolder) holder).setData(
                false,
                currentPosDayLD.getDayOfWeek().toString(), String.valueOf(currentPosDayLD.getDayOfMonth()));
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
