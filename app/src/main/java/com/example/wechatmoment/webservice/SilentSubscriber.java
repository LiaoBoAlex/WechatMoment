package com.example.wechatmoment.webservice;

import android.content.Context;
import android.widget.Toast;


import com.example.wechatmoment.R;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by liaobo on 2017/12/4.
 */

public class SilentSubscriber<T> extends DisposableObserver<T>{

    private SubscriberListener mSubscriberListener;
    private Context context;

    public SilentSubscriber(SubscriberListener mSubscriberListener, Context context) {
        this.mSubscriberListener = mSubscriberListener;
        this.context = context;
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onError(Throwable e) {
        Toast.makeText(context, context.getString(R.string.network_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNext(T t) {
        if (mSubscriberListener != null) {
            mSubscriberListener.onNext(t);
        }
    }
}
