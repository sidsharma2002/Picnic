package com.example.picnic.common.concurrency;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.WorkerThread;

import java.util.concurrent.CountDownLatch;

public class UiThreadPoster {

    private Handler handler = getHandler();

    // override for testing only.
    protected Handler getHandler() {
        return new Handler(Looper.getMainLooper());
    }

    public void post(Runnable runnable) {
        handler.post(runnable);
    }

    @WorkerThread
    public void postAndWait(Runnable runnable) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);

        handler.post(() -> {
            runnable.run();
            latch.countDown();
        });

        latch.await();
    }

    public void postDelayed(Runnable runnable, Long timeMillis) {
        handler.postDelayed(runnable, timeMillis);
    }

}
