package com.example.picnic.screens.home;

import static com.example.picnic.screens.home.FetchLocallyStoredPhotosUseCase.*;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import com.example.picnic.common.observable.BaseObservable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FetchLocallyStoredPhotosUseCase extends BaseObservable<Listener> {

    private final Context context;

    public FetchLocallyStoredPhotosUseCase(Context context) {
        this.context = context;
    }

    public interface Listener {
        void onImagesFetched(List<Uri> images, int pageNo, int offset);
        void onFailure(String reason);
    }

    public void execute() {

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        ArrayList<Uri> galleryImageUrls = new ArrayList<>();

        String[] columns = new String[2];
        columns[0] = MediaStore.Images.Media.DATA;
        columns[1] = MediaStore.Images.Media.BUCKET_DISPLAY_NAME;

        String orderBy = MediaStore.Images.Media.DATE_TAKEN;

        Cursor cursor = context.getContentResolver().query(
                uri,
                columns,
                null,
                null,
                orderBy + " DESC"
        );

        if (cursor == null) {
            for (Listener listener : getObservers()) {
                listener.onFailure("cursor is null");
            }
            return;
        }

        int idColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA);

        while (cursor.moveToNext()) {
            long id = cursor.getLong(idColumn);
            galleryImageUrls.add(ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id));
        }

        cursor.close();

        for (Listener listener : getObservers()) {
            listener.onImagesFetched(galleryImageUrls, 0, 0);
        }
    }
}
