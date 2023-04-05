package com.example.picnic.screens.onboarding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.picnic.R;

public class OnBoardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        ViewPager2 viewPager2 = findViewById(R.id.viewpager_onboard);

        viewPager2.setAdapter(new RecyclerView.Adapter() {

            class ItemVH extends RecyclerView.ViewHolder {

                public ItemVH(@NonNull View itemView) {
                    super(itemView);
                    ivContent = itemView.findViewById(R.id.iv_content);
                    tvContent = itemView.findViewById(R.id.tv_content);
                    tvDesc = itemView.findViewById(R.id.tv_desc);
                }

                private ImageView ivContent;
                private TextView tvContent;
                private TextView tvDesc;

                public void setData() {
                    int pos = getAdapterPosition();
                    if (pos == 0) {
                        ivContent.setImageDrawable(getResources().getDrawable(R.drawable.onboarding2));
                        tvContent.setText("Super Adaptive");
                        tvDesc.setText("We are designed to learn from you, adapting to your changing needs and preferences over time.");
                    } else if (pos == 1) {
                        ivContent.setImageDrawable(getResources().getDrawable(R.drawable.onboarding3));
                        tvContent.setText("Works Offline");
                        tvDesc.setText("Are you tired of being tethered to an internet connection for your favorite apps? Say hello to this fully offline app.");
                    }
                }

            }

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_onboarding, parent, false);
                return new ItemVH(view);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                ((ItemVH) holder).setData();
            }

            @Override
            public int getItemCount() {
                return 2;
            }
        });
    }
}
