package com.example.wechatmoment.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.wechatmoment.R;
import com.example.wechatmoment.bean.Moment;
import com.example.wechatmoment.webservice.GetUserAndPostResponse;
import com.example.wechatmoment.webservice.ServiceClient;
import com.example.wechatmoment.webservice.SilentSubscriber;
import com.example.wechatmoment.webservice.SubscriberListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        userName = "jsmith";

        loadData();
    }

    private void loadData(){
        SubscriberListener mListener = new SubscriberListener<GetUserAndPostResponse>() {
                @Override
                public void onNext(GetUserAndPostResponse result) {
                    if(result.getUser()!=null) {
                        List<Moment> momentList = new ArrayList<>();
                        if(result.getMomentList()!=null && result.getMomentList().size()>0){
                            for(Moment m:result.getMomentList()){
                                if(m.getSender()!=null &&
                                        ((m.getContent()!=null && !m.getContent().isEmpty()) || (m.getImages()!=null && m.getImages().size()>0))){
                                    momentList.add(m);
                                }

                            }
                        }
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        MomentAdapter momentAdapter = new MomentAdapter(MainActivity.this, result.getUser(), momentList);
                        mRecyclerView.setAdapter(momentAdapter);
                    }
                }
            };
            ServiceClient.getInstance().getUserMoment(new SilentSubscriber(mListener, MainActivity.this),
                    userName);
    }
}
