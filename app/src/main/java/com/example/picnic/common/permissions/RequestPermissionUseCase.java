package com.example.picnic.common.permissions;

import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.List;

public class RequestPermissionUseCase {

    public RequestPermissionUseCase(AppCompatActivity activity) {
        this.activity = activity;
    }

    public interface Listener {
        void onPermissionGranted();
        void onPermissionRejected();
    }

    private final AppCompatActivity activity;

    public void request(String permission, Listener listener) {

        String[] permissionArr = {permission};

        activity.registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {

            if (Boolean.TRUE.equals(result.get(permissionArr[0])))
                listener.onPermissionGranted();
            else
                listener.onPermissionRejected();

        }).launch(permissionArr);
    }

}
