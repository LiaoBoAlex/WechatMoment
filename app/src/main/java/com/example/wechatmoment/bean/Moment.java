package com.example.wechatmoment.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liaobo on 2017/12/21.
 */

public class Moment implements Serializable {
    private List<Image> images;
    private String content;
    private List<Comment> comments;
    private User sender;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
}
