package com.example.picnic.screens.imageDetail.views;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.picnic.R;
import com.example.picnic.common.image.ImageLoader;
import com.example.picnic.common.utils.ScreenUtils;
import com.example.picnic.usecases.faceDetection.DetectedFacesData;
import com.google.mlkit.vision.face.Face;

public class ImageDetailViewMvcImpl implements ImageDetailViewMvc {

    public ImageDetailViewMvcImpl(LayoutInflater layoutInflater, ImageLoader imageLoader, ScreenUtils screenUtils, DetectedFacesData detectedFacesData) {
        rootView = layoutInflater.inflate(R.layout.fragment_image_detail, null, false);
        this.imageLoader = imageLoader;
        this.screenUtils = screenUtils;
        ivDetailedImage = rootView.findViewById(R.id.iv_detailed_image);

        setupUi(detectedFacesData);
    }

    private final View rootView;
    private final ImageLoader imageLoader;
    private final ImageView ivDetailedImage;
    private final ScreenUtils screenUtils;

    private void setupUi(DetectedFacesData detectedFacesData) {
        imageLoader.loadFromPath(ivDetailedImage, detectedFacesData.getImagePath());
        drawFaceBounds(detectedFacesData.getFaceList().get(0));
    }

    private void drawFaceBounds(Face face) {
        Rect rect = face.getBoundingBox();
        Log.d("bounding box : ", rect.toString());

        View view = new View(ivDetailedImage.getContext());
        // set view's prop
        view.setId(View.generateViewId());
        view.setBackgroundColor(Color.WHITE);
        view.setElevation(20f);
        view.setAlpha(0.5f);
        view.setPadding(30, 30, 30, 30); // for large click area

        ConstraintLayout constraintLayout = (ConstraintLayout) ivDetailedImage.getParent();
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(rect.width(), rect.height());
        view.setLayoutParams(layoutParams);
        constraintLayout.addView(view);

        // set constraints
        ConstraintSet set = new ConstraintSet();
        set.clone(constraintLayout);

        set.connect(view.getId(),
                ConstraintSet.START,
                ivDetailedImage.getId(),
                ConstraintSet.START,
                rect.left);

        set.connect(view.getId(),
                ConstraintSet.TOP,
                ivDetailedImage.getId(),
                ConstraintSet.TOP,
                rect.top - screenUtils.getNavBarHeight((Activity) rootView.getContext()));

        set.applyTo(constraintLayout);
        constraintLayout.invalidate();

        // setZoomClickListener(view);
    }

    private void setZoomClickListener(View view) {
        view.setOnClickListener(v -> {
            Rect rect1 = new Rect();
            Rect rect2 = new Rect();

            ivDetailedImage.getGlobalVisibleRect(rect1);
            v.getGlobalVisibleRect(rect2);

            ivDetailedImage.animate()
                    .translationXBy(rect1.centerX() - rect2.centerX())
                    .translationYBy(rect1.centerY() - rect2.centerY())
                    .start();
        });
    }

    @Override
    public View getRootView() {
        return rootView;
    }
}
