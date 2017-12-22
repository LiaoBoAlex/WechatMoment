package com.example.wechatmoment.webservice;


import com.example.wechatmoment.Constants;
import com.example.wechatmoment.bean.Comment;
import com.example.wechatmoment.bean.Moment;
import com.example.wechatmoment.bean.User;

import java.util.ArrayList;
import java.util.List;

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
                        List<Moment> resultMoment = webService.getUserTweet(Constants.SERVER_URL + userName + "/tweets").execute().body();
                        List<Moment> momentList = new ArrayList<>();
                        if(resultMoment!=null && resultMoment.size()>0){
                            for(Moment m:resultMoment){
                                if(m.getSender()!=null &&
                                        ((m.getContent()!=null && !m.getContent().isEmpty()) || (m.getImages()!=null && m.getImages().size()>0))){
                                    momentList.add(m);
                                }
                            }
                        }
                        response.setMomentList(momentList);
                        return response;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


}
