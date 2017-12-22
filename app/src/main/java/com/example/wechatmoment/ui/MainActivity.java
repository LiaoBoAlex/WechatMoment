package com.example.wechatmoment.ui;

import android.os.Bundle;
import android.support.v4.widget.RefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.wechatmoment.R;
import com.example.wechatmoment.webservice.GetUserAndPostResponse;
import com.example.wechatmoment.webservice.ServiceClient;
import com.example.wechatmoment.webservice.SilentSubscriber;
import com.example.wechatmoment.webservice.SubscriberListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshlayout)
    RefreshLayout mRefreshLayout;

    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        userName = "jsmith";
        mRefreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        loadData();
    }

    private void loadData(){
        SubscriberListener mListener = new SubscriberListener<GetUserAndPostResponse>() {
                @Override
                public void onNext(GetUserAndPostResponse result) {
                    bindData(result);
                    mRefreshLayout.setRefreshing(false);
                }
            };
            ServiceClient.getInstance().getUserMoment(new SilentSubscriber(mListener, MainActivity.this),
                    userName);
    }

    private void bindData(GetUserAndPostResponse result){
        if(result.getUser()!=null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            MomentAdapter momentAdapter = new MomentAdapter(MainActivity.this, result.getUser(), result.getMomentList());
            mRecyclerView.setAdapter(momentAdapter);
        }
    }
}
