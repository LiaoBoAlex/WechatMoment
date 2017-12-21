package com.example.wechatmoment.webservice;

/**
 * Created by Mloong on 2017/11/14.
 */

public interface SubscriberListener<T> {
    void onNext(T t);
}
