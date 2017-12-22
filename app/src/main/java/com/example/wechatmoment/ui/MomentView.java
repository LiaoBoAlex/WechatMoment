package com.example.wechatmoment.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wechatmoment.R;
import com.example.wechatmoment.bean.Comment;
import com.example.wechatmoment.bean.Image;
import com.example.wechatmoment.bean.Moment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liaobo on 2017/12/21.
 */

public class MomentView extends FrameLayout {
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.rv_image)
    RecyclerView rvImages;
    @BindView(R.id.rv_comment)
    RecyclerView rvComments;

    private Context mContext;

    public MomentView(Context context) {
        super(context);
        mContext = context;
        init();

    }

    public MomentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init(){
        LayoutInflater.from(mContext).inflate(R.layout.view_post, this);
        ButterKnife.bind(this);
    }

    public void setData(Moment moment){
        Glide.with(mContext).load(moment.getSender().getAvatar()).error(R.drawable.profile_placeholder).placeholder(R.color.gray).into(ivAvatar);
        tvName.setText(moment.getSender().getNick());
        if(moment.getContent()!=null && !moment.getContent().isEmpty()){
            tvContent.setVisibility(VISIBLE);
            tvContent.setText(moment.getContent());
        }else
            tvContent.setVisibility(GONE);

        if(moment.getImages()!=null && moment.getImages().size()>0){
            rvImages.setVisibility(VISIBLE);
            rvImages.setLayoutManager(new GridLayoutManager(mContext, 3));
            PicGridAdapter gridAdapter = new PicGridAdapter(moment.getImages());
            rvImages.setAdapter(gridAdapter);
        }else
            rvImages.setVisibility(GONE);

        if(moment.getComments()!= null && moment.getComments().size()>0){
            rvComments.setVisibility(VISIBLE);
            rvComments.setLayoutManager(new LinearLayoutManager(mContext));
            CommentAdapter commentAdapter = new CommentAdapter(moment.getComments());
            rvComments.setAdapter(commentAdapter);
        }else
            rvComments.setVisibility(GONE);
    }


    public class PicGridAdapter extends RecyclerView.Adapter<PicGridAdapter.ViewHolder> {
        private List<Image> imageList = new ArrayList<>();

        public PicGridAdapter(List<Image> imageList) {
                this.imageList = imageList;
                if(imageList!=null && imageList.size()==4)
                    this.imageList.add(2, new Image());
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            if(imageList.get(position).getUrl()!=null) {
                Glide.with(mContext).load(imageList.get(position).getUrl()).error(R.drawable.error).placeholder(R.color.gray).into(holder.tvImage);
                holder.tvImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, "pic " + imageList.get(position).getUrl() +" is pressed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            if(imageList == null)
                return 0;
            else
                return imageList.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView tvImage;
            public ViewHolder(View itemView) {
                super(itemView);
                tvImage = (ImageView) itemView.findViewById(R.id.iv_image);
            }
        }
    }

    public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
        private List<Comment> commentList;
        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvComment;
            public MyViewHolder(View view) {
                super(view);
                tvComment = (TextView) itemView.findViewById(R.id.tv_comment);
            }
        }

        public CommentAdapter(List<Comment> commentList) {
            this.commentList = commentList;
        }

        @Override
        public CommentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_comment, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            Comment comment = commentList.get(position);
            String myString = comment.getSender().getNick() + ":" + comment.getContent();
            SpannableString str = new SpannableString(myString);
            str.setSpan(new UserClickText(mContext, comment.getSender().getNick()),0,comment.getSender().getNick().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            str.setSpan(new ContentClickText(mContext, comment.getContent()),comment.getSender().getNick().length()+1,myString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tvComment.setText(str);
            holder.tvComment.setMovementMethod(LinkMovementMethod.getInstance());//不设置 没有点击事件
            holder.tvComment.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明
        }

        @Override
        public int getItemCount() {
            if(commentList == null)
                return 0;
            else
                return commentList.size();
        }
    }

    class UserClickText extends ClickableSpan {
        private Context context;
        private String userName;

        public UserClickText(Context context, String userName) {
            this.context = context;
            this.userName = userName;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(Color.BLUE);
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(View widget) {
            Toast.makeText(context,"user " + userName + " is clicked!", Toast.LENGTH_SHORT).show();
        }
    }

    class ContentClickText extends ClickableSpan {
        private Context context;
        private String content;

        public ContentClickText(Context context, String content) {
            this.context = context;
            this.content = content;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(Color.BLACK);
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(View widget) {
            Toast.makeText(context,"content " + content + " is clicked!", Toast.LENGTH_SHORT).show();
        }
    }
}
