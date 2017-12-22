package com.example.wechatmoment.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.wechatmoment.R;
import com.example.wechatmoment.bean.Moment;
import com.example.wechatmoment.bean.User;

import java.util.List;

/**
 * Created by liaobo on 2017/12/21.
 */

public class MomentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private User user;
    private List<Moment> momentList;
    private Context mContext;

    private final int TYPE_PROFILE = 100;
    private final int TYPE_MOMENT = 101;

    public MomentAdapter(Context mContext, User user, List<Moment> momentList) {
        this.user = user;
        this.momentList = momentList;
        this.mContext = mContext;
    }

    public class ProfileHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar, ivProfile;
        TextView tvName;
        public ProfileHolder(View view) {
            super(view);
            ivAvatar = (ImageView) view.findViewById(R.id.iv_avatar);
            ivProfile = (ImageView) view.findViewById(R.id.iv_profile);
            tvName = (TextView) view.findViewById(R.id.tv_name);
        }
    }

    public class MomentHolder extends RecyclerView.ViewHolder {
        MomentView momentView;
        public MomentHolder(View view) {
            super(view);
            momentView = (MomentView) view.findViewById(R.id.moment_view);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_PROFILE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile, parent, false);
                return new ProfileHolder(view);
            case TYPE_MOMENT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
                return new MomentHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case TYPE_PROFILE:
                final ProfileHolder profileHolder = (ProfileHolder) holder;
                Glide.with(mContext).load(user.getAvatar()).into(profileHolder.ivAvatar);
                //This allow the image be loaded only once, to prevent failure image keep reloading when user scrolling. this could be implement to all the
                //imageview if required.
                if(profileHolder.ivProfile.getTag()!="error") {
                    Glide.with(mContext)
                            .load(user.getProfileImage())
                            .error(R.drawable.error)
                            .placeholder(R.color.gray)
                            .listener(new RequestListener<String, GlideDrawable>() {
                                @Override
                                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                    profileHolder.ivProfile.setTag("error");
                                    return false;

                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    return false;
                                }
                            })
                            .into(profileHolder.ivProfile);
                }
                profileHolder.tvName.setText(user.getNick());
                break;
            case TYPE_MOMENT:
                ((MomentHolder) holder).momentView.setData(momentList.get(position - 1));
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
            return TYPE_PROFILE;
        else
            return TYPE_MOMENT;
    }

    @Override
    public int getItemCount() {
        if(momentList == null)
            return 1;
        else
            return momentList.size() + 1;
    }
}