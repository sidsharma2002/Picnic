package com.example.picnic.common.concurrency;

import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BgThreadPoster {

    private ThreadPoolExecutor bgExecutor = new ThreadPoolExecutor(
            3,
            Integer.MAX_VALUE,
            1,
            TimeUnit.MINUTES,
            new SynchronousQueue<>());


    public Future<?> post(Runnable runnable) {
        return bgExecutor.submit(runnable);
    }

}
