package com.example.picnic.common.observable;

import com.example.picnic.common.concurrency.UiThreadPoster;

@SuppressWarnings("unchecked")
public class ObservableDataHolder<T> extends BaseObservable<ObservableDataHolder.Observer<T>> {

    public ObservableDataHolder() {
        uiThreadPoster = new UiThreadPoster();
        this.mData = NOT_SET;
    }

    public ObservableDataHolder(T initialValue) {
        uiThreadPoster = new UiThreadPoster();
        mData = initialValue;
    }

    public interface Observer<T> {
        void onDataChanged(T data);
    }

    private final UiThreadPoster uiThreadPoster;
    private final Object LOCK = new Object();

    private final Object NOT_SET = new Object();
    private Object mData;

    protected void setData(T newValue) {
        synchronized (LOCK) {
            mData = newValue;

            uiThreadPoster.post(() -> {
                for (Observer<T> observer : getObservers()) {
                    observer.onDataChanged(newValue);
                }
            });
        }
    }

    public T getData() {
        synchronized (LOCK) {
            if (mData == NOT_SET)
                return null;
            else
                return (T) mData;
        }
    }

    @Override
    public void register(Observer<T> observer) {
        super.register(observer);

        // notify the observer of current value on registration.
        if (mData != NOT_SET)
            observer.onDataChanged((T) mData);
    }

}
