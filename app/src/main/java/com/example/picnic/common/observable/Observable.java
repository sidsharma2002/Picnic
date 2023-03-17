package com.example.picnic.common.observable;

public interface Observable<Observer> {
    void register(Observer observer);
    void unregister(Observer observer);
}
