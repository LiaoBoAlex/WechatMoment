package com.example.wechatmoment.webservice;

import com.example.wechatmoment.bean.Moment;
import com.example.wechatmoment.bean.User;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liaobo on 2017/12/21.
 */

public class GetUserAndPostResponse implements Serializable {
    private User user;
    private List<Moment> momentList;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Moment> getMomentList() {
        return momentList;
    }

    public void setMomentList(List<Moment> momentList) {
        this.momentList = momentList;
    }
}
