package com.example.wechatmoment.webservice;


import com.example.wechatmoment.Constants;
import com.example.wechatmoment.bean.User;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liaobo on 4/27/2017.
 */

public class ServiceClient {

    private WebService webService;

    private ServiceClient() {
        webService = RetrofitClient.getClient(Constants.SERVER_URL).create(WebService.class);
    }

    private static class SingletonHolder{
        private static final ServiceClient INSTANCE = new ServiceClient();
    }

    public static ServiceClient getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public void getUserMoment(DisposableObserver subscriber, final String userName){
        webService.getUserInfo(Constants.SERVER_URL+userName)
                .observeOn(Schedulers.io())
                .map(new Function<User, GetUserAndPostResponse>() {
                    @Override
                    public GetUserAndPostResponse apply(User user) throws Exception {
                        GetUserAndPostResponse response = new GetUserAndPostResponse();
                        response.setUser(user);
                        response.setMomentList(
                                webService.getUserTweet(Constants.SERVER_URL + userName + "/tweets").execute().body());
                        return response;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


}
