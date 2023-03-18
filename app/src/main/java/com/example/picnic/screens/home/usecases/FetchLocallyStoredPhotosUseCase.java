package com.example.picnic.screens.home.usecases;

import static com.example.picnic.screens.home.usecases.FetchLocallyStoredPhotosUseCase.*;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.picnic.common.observable.BaseObservable;
import com.example.picnic.common.concurrency.BgThreadPoster;
import com.example.picnic.common.concurrency.UiThreadPoster;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FetchLocallyStoredPhotosUseCase extends BaseObservable<Listener> {

    public FetchLocallyStoredPhotosUseCase(Context context, BgThreadPoster bgThreadPoster, UiThreadPoster uiThreadPoster) {
        this.context = context;
        this.bgThreadPoster = bgThreadPoster;
        this.uiThreadPoster = uiThreadPoster;
    }

    private final Context context;
    private final BgThreadPoster bgThreadPoster;
    private final UiThreadPoster uiThreadPoster;

    public interface Listener {
        void onImagesFetched(List<String> images, int pageNo, int offset);

        void onFailure(String reason);
    }

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
                uiThreadPoster.post(() -> {
                    for (Listener listener : getObservers()) {
                        listener.onFailure("cursor is null");
                    }
                });

                return;
            }

            int idColumn = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);
            ArrayList<String> galleryImageUrls = new ArrayList<>();

            while (cursor.moveToNext()) {
                long id = cursor.getLong(idColumn);
                Uri uri = (ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id));
                Log.d("debug uri", "uri : " + uri);
                Log.d("debug string", "string : " + cursor.getString(idColumn));
                galleryImageUrls.add(cursor.getString(idColumn)); // NOTE : normal uri can't be parsed so always send this parsed string path.
            }

            cursor.close();

            // notify of success
            uiThreadPoster.post(() -> {
                for (Listener listener : getObservers()) {
                    listener.onImagesFetched(galleryImageUrls, 0, 0);
                }
            });
        });
    }
}
