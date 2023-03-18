package com.example.picnic.common.observable;

public class MutableObservableDataHolder<T> extends ObservableDataHolder<T> {

    @Override
    public void setData(T newValue) { // breaking liskov's rule ?
        super.setData(newValue);
    }
}
