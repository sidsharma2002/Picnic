package com.example.picnic.faceDetection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;

import com.example.picnic.app.PicnicApp;
import com.example.picnic.common.concurrency.BgThreadPoster;
import com.example.picnic.common.concurrency.UiThreadPoster;
import com.example.picnic.common.image.BitmapUtils;
import com.example.picnic.common.observable.MutableObservableDataHolder;
import com.example.picnic.common.observable.ObservableDataHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetectorOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FaceDetector {

    private final Context context;
    private final com.google.mlkit.vision.face.FaceDetector detector;
    private final BgThreadPoster bgThreadPoster;
    private final UiThreadPoster uiThreadPoster;

    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    public FaceDetector(PicnicApp context, BgThreadPoster bgThreadPoster, UiThreadPoster uiThreadPoster) {
        this.context = context;

        this.bgThreadPoster = bgThreadPoster;
        this.uiThreadPoster = uiThreadPoster;

        FaceDetectorOptions options = new FaceDetectorOptions.Builder()
                .enableTracking()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                .build();
        detector = FaceDetection.getClient(options);
    }

    private MutableObservableDataHolder<String> _obsFaces = new MutableObservableDataHolder<>();
    public ObservableDataHolder<String> obsFaces = _obsFaces;


    public void detectFaceAndNotify(String path) {
        executor.submit(() -> {
            Bitmap myBitmap = BitmapFactory.decodeFile(path);

            Log.d(this.getClass().getSimpleName(), "submitting bitmap " + myBitmap);

            List<Face> result = detectSync(myBitmap);

            if (result == null) return;

            _obsFaces.setData(path);
        });
    }


    private List<Face> detectSync(Bitmap bitmap) {
        try {
            InputImage targetImage;
            targetImage = InputImage.fromByteArray(new BitmapUtils().getByteArrayFromBitmap(bitmap),
                    bitmap.getWidth(),
                    bitmap.getHeight(),
                    0,
                    InputImage.IMAGE_FORMAT_NV21);

            CountDownLatch latch = new CountDownLatch(1);

            List<Face> detectedFaces = new ArrayList<>();

            detector.process(targetImage).addOnCompleteListener(task -> {
                detectedFaces.addAll(task.getResult());
                latch.countDown();
            });

            latch.await(1, TimeUnit.MINUTES);

            if (!detectedFaces.isEmpty())
                Log.d("face detection",
                        "face detected size : " + detectedFaces.size()
                                + " tracking id : " + detectedFaces.get(0).getTrackingId());

            if (detectedFaces.size() > 0)
                return detectedFaces;

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
