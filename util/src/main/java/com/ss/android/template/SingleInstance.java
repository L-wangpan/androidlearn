package com.ss.android.template;

public abstract class SingleInstance<T> {
    private T mInstance;

    protected abstract T create();

    public final T get() {
        if (null == mInstance) {
            synchronized (this) {
                if (null == mInstance) {
                    return create();
                }
            }
        }
        return mInstance;
    }
}
