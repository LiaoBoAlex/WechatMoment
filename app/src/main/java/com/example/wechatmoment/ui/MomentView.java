package com.example.wechatmoment.ui;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wechatmoment.R;
import com.example.wechatmoment.bean.Comment;
import com.example.wechatmoment.bean.Image;
import com.example.wechatmoment.bean.Moment;

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
        LayoutInflater.from(mContext).inflate(R.layout.item_post, this);
        ButterKnife.bind(this);
    }

    public void setData(Moment moment){
        Glide.with(mContext).load(moment.getSender().getAvatar()).error(R.drawable.profile_placeholder).placeholder(R.drawable.profile_placeholder).into(ivAvatar);
        tvName.setText(moment.getSender().getNick());
        tvContent.setText(moment.getContent());

        rvImages.setLayoutManager(new GridLayoutManager(mContext, 3));
        PicGridAdapter gridAdapter = new PicGridAdapter(moment.getImages());
        rvImages.setAdapter(gridAdapter);

        rvComments.setLayoutManager(new LinearLayoutManager(mContext));
        CommentAdapter commentAdapter = new CommentAdapter(moment.getComments());
        rvComments.setAdapter(commentAdapter);

    }


    public class PicGridAdapter extends RecyclerView.Adapter<PicGridAdapter.ViewHolder> {
        private List<Image> imageList;

        public PicGridAdapter(List<Image> imageList) {
            this.imageList = imageList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Glide.with(mContext).load(/*imageList.get(position).getUrl()*/"http://image-product-web.oss-cn-beijing.aliyuncs.com/artificer/20171220181659.png").error(R.drawable.profile_placeholder).placeholder(R.drawable.profile_placeholder).into(holder.tvImage);
            holder.tvImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
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
            holder.tvComment.setText(comment.getSender().getNick() + ":" + comment.getContent());
        }

        @Override
        public int getItemCount() {
            if(commentList == null)
                return 0;
            else
                return commentList.size();
        }
    }
}
