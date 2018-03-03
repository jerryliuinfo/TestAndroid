package com.apache.android.rx;

/**
 * Created by 01370340 on 2017/9/4.
 */

public interface MyObserver<T> {

    void onNext(T t);

    void onComplete();

    void onError(Throwable e);
}
