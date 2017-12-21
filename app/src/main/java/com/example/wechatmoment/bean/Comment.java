package com.example.wechatmoment.bean;

import java.io.Serializable;

/**
 * Created by liaobo on 2017/12/21.
 */

public class Comment implements Serializable {
    private String content;
    private User sender;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
}
