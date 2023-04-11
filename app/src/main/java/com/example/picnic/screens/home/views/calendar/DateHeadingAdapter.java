package com.example.picnic.screens.home.views.calendar;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.picnic.R;

public class DateHeadingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // viewholder

    static class DateViewHolder extends RecyclerView.ViewHolder {

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            tvDay = itemView.findViewById(R.id.tv_day);
            tvDate = itemView.findViewById(R.id.tv_date);
            parent = itemView.findViewById(R.id.cv_dates_heading);
            view = itemView.findViewById(R.id.ninja);
        }

        static public DateViewHolder getInstance(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dates_heading, parent, false);
            return new DateViewHolder(view);
        }

        private final CardView parent;
        private final TextView tvDay;
        private final TextView tvDate;
        private final View view;
        private final Context context;

        public void setData(Boolean isToday, String day, String date) {
            tvDate.setText(day);
            tvDate.setText(date);

            if (isToday) {
                parent.setCardBackgroundColor(context.getColor(R.color.purple_500)); // couldn't find reliable way to get themed color like colorPrimary.
                tvDay.setTextColor(context.getColor(R.color.white));
                tvDate.setTextColor(context.getColor(R.color.white));
            } else {
                view.setVisibility(View.GONE);
                parent.setCardBackgroundColor(context.getColor(R.color.white));
                tvDay.setTextColor(context.getColor(R.color.black));
                tvDate.setTextColor(context.getColor(R.color.black));
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return DateViewHolder.getInstance(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((DateViewHolder) holder).setData(
                position == 6,
                "WED", String.valueOf(position));

    }

    @Override
    public int getItemCount() {
        return 20;
    }
}
