package com.example.wechatmoment.webservice;

/**
 * Created by liaobo on 4/27/2017.
 */

import com.example.wechatmoment.bean.Moment;
import com.example.wechatmoment.bean.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface WebService {

    @GET
    Observable<User> getUserInfo(@Url String url);

    @GET
    Call<List<Moment>> getUserTweet(@Url String url);
}
