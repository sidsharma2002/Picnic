package com.example.picnic.screens.home.usecases;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.picnic.common.concurrency.BgThreadPoster;
import com.example.picnic.common.concurrency.UiThreadPoster;
import com.example.picnic.common.observable.MutableObservableDataHolder;
import com.example.picnic.common.observable.ObservableDataHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FetchPhotosFromStorageUseCase {

    public FetchPhotosFromStorageUseCase(Context context, BgThreadPoster bgThreadPoster, UiThreadPoster uiThreadPoster) {
        this.context = context;
        this.bgThreadPoster = bgThreadPoster;
        this.uiThreadPoster = uiThreadPoster;
    }

    private final Context context;
    private final BgThreadPoster bgThreadPoster;
    private final UiThreadPoster uiThreadPoster;

    private MutableObservableDataHolder<List<String>> _obsImagePaths = new MutableObservableDataHolder<>();
    public ObservableDataHolder<List<String>> obsImagePaths = _obsImagePaths;

    public void executeAsync() {
        bgThreadPoster.post(() -> {
            String[] columns = new String[1];
            columns[0] = MediaStore.MediaColumns.DATA;

            String orderBy = MediaStore.Images.Media.DATE_TAKEN;

            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    columns,
                    null,
                    null,
                    orderBy + " DESC"
            );

            if (cursor == null) {
                // notify of failure
                // TODO
                return;
            }

            int idColumn = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);
            ArrayList<String> galleryImageUrls = new ArrayList<>();

            while (cursor.moveToNext()) {
                long id = cursor.getLong(idColumn);
                Uri uri = (ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id));
                Log.d("debug string", "uri : " + uri + " + string : " + cursor.getString(idColumn));
                galleryImageUrls.add(cursor.getString(idColumn)); // NOTE : normal uri doesn't work so always send this parsed string path.
            }

            cursor.close();

            _obsImagePaths.setData(Collections.unmodifiableList(galleryImageUrls));
        });
    }
}
