package com.example.picnic.screens.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.picnic.app.PicnicApp;
import com.example.picnic.di.activity.usecases.ActivityUseCaseFactory;
import com.example.picnic.di.application.AppCompositionRoot;
import com.example.picnic.di.application.viewMvcs.ViewMvcFactory;
import com.example.picnic.screens.home.controllers.HomeFragmentController;
import com.example.picnic.screens.home.views.HomeScreenViewMvc;

public class HomeFragment extends Fragment {

    private AppCompositionRoot appCompRoot;
    private ViewMvcFactory viewMvcFactory;
    private HomeFragmentController controller;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appCompRoot = ((PicnicApp) (getContext().getApplicationContext())).getAppCompositionRoot();

        this.viewMvcFactory = appCompRoot.getViewMvcFactory();

        ActivityUseCaseFactory activityFactory = appCompRoot.getNewActivityUseCaseFactory((AppCompatActivity) getActivity());
        controller = appCompRoot.getControllerFactory().getHomeFragmentController(activityFactory);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        HomeScreenViewMvc homeScreenViewMvc = viewMvcFactory.getHomeScreenViewMvc(inflater);
        controller.bindViewMvc(homeScreenViewMvc);

        return homeScreenViewMvc.getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller.onViewCreated();
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
