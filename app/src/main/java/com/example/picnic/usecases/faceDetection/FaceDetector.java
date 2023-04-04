package com.example.picnic.usecases.faceDetection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.picnic.app.PicnicApp;
import com.example.picnic.common.image.BitmapUtils;
import com.example.picnic.common.observable.MutableObservableDataHolder;
import com.example.picnic.common.observable.ObservableDataHolder;
import com.example.picnic.usecases.StoreFaceDataToStorageUseCase;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetectorOptions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FaceDetector {

    private final Context context;
    private final com.google.mlkit.vision.face.FaceDetector detector;
    private final StoreFaceDataToStorageUseCase storeFaceDataToStorageUseCase;

    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    public FaceDetector(PicnicApp context, StoreFaceDataToStorageUseCase storeFaceDataToStorageUseCase) {
        this.context = context;
        this.storeFaceDataToStorageUseCase = storeFaceDataToStorageUseCase;

        FaceDetectorOptions options = new FaceDetectorOptions.Builder()
                .enableTracking()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                .build();

        detector = FaceDetection.getClient(options);
    }

    private MutableObservableDataHolder<DetectedFacesData> _obsFaces = new MutableObservableDataHolder<>();
    public ObservableDataHolder<DetectedFacesData> obsFaces = _obsFaces;

    public void detectFaceAndNotify(String path) {
        executor.submit(() -> {
            Bitmap myBitmap = BitmapFactory.decodeFile(path);

            Log.d(this.getClass().getSimpleName(), "submitting bitmap " + myBitmap);

            List<Face> result = detectSync(myBitmap);

            if (result == null || result.isEmpty()) return;

            storeFaceDataToStorageUseCase.storeSync(path, result);

            _obsFaces.setData(new DetectedFacesData(path, result));
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


            return detectedFaces;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
