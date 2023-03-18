package com.example.picnic.common.observable;

import androidx.annotation.MainThread;

public interface Observable<Observer> {
    @MainThread
    void register(Observer observer);

    @MainThread
    void unregister(Observer observer);
}
