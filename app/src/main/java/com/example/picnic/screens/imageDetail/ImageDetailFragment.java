package com.example.picnic.screens.imageDetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.picnic.app.PicnicApp;
import com.example.picnic.di.application.AppCompositionRoot;
import com.example.picnic.di.application.usecases.UseCaseFactory;
import com.example.picnic.di.application.viewMvcs.ViewMvcFactory;
import com.example.picnic.screens.imageDetail.controller.ImageDetailFragmentController;
import com.example.picnic.screens.imageDetail.views.ImageDetailViewMvc;
import com.example.picnic.usecases.faceDetection.DetectedFacesData;

public class ImageDetailFragment extends Fragment {

    private ViewMvcFactory viewMvcFactory;
    private UseCaseFactory useCaseFactory;
    private ImageDetailFragmentController controller;
    private DetectedFacesData detectedFacesData;

    public static ImageDetailFragment getInstance(DetectedFacesData detectedFaceData) {
        ImageDetailFragment fragment = new ImageDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", detectedFaceData);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompositionRoot appCompRoot = ((PicnicApp) (getContext().getApplicationContext())).getAppCompositionRoot();

        viewMvcFactory = appCompRoot.getViewMvcFactory();
        useCaseFactory = appCompRoot.getUseCaseFactory();

        detectedFacesData = (DetectedFacesData) getArguments().getSerializable("data");
        controller = appCompRoot.getControllerFactory().getImageDetailFragmentController(detectedFacesData);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        ImageDetailViewMvc viewMvc = viewMvcFactory.getImageDetailViewMvc(inflater, detectedFacesData);
        controller.bindViewMvc(viewMvc);

        return viewMvc.getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        controller.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        controller.onStop();
    }
}
