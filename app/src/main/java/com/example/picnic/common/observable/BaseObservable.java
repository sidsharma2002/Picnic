package com.example.picnic.common.observable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BaseObservable<Observer> implements Observable<Observer> {

    private List<Observer> observers = new ArrayList<>();

    @Override
    public void register(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unregister(Observer observer) {
        observers.remove(observer);
    }

    protected List<Observer> getObservers() {
        return Collections.unmodifiableList(observers);
    }

}
