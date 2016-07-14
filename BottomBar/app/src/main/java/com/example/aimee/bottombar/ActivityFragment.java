package com.example.aimee.bottombar;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.aimee.bottombar.Adapter.ActivityItemRecyclerViewAdapter;
import com.example.aimee.bottombar.newServer.model.MActivityModel;
import com.example.aimee.bottombar.newServer.request.MActivityRequest;
import com.example.aimee.bottombar.newServer.response.MActivityResponse;
import com.example.aimee.bottombar.newServer.utils.statics.Global;
import com.example.aimee.bottombar.newServer.utils.statics.NewHttpFactory;

import java.util.List;

/**
 * Created by Aimee on 2016/3/20.
 */
//这个界面大概好了,用来卡片式的展示活动,并且可以根据标题显示不同的内容
public class ActivityFragment extends Fragment{
    private RecyclerView recyclerView;
    private ActivityItemRecyclerViewAdapter activityListAdapter;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private int type = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_activity, container, false);
        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycle_view);
        //recyclerView = (RecyclerView) v.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar.setVisibility(View.VISIBLE);
        toolbar= (Toolbar) v.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        if(getActivity().getTitle().equals("嗨学")) {
            mTitle.setText("最新活动");
            type = 0;
        }
        else
        {
            Intent i=getActivity().getIntent();
            String title=i.getExtras().getString("key");
            mTitle.setText(title);
            switch (title){
                case"走进历史":
                    type = 1;
                    break;
                case"科学天地":
                    type = 2;
                    break;
                case"志愿活动":
                    type = 3;
                    break;
                case"周边出游":
                    type =4;
                    break;
                case"手工DIY":
                    type = 5;
                    break;
                case"知识库":
                    type = 6;
                    break;
                default:type = 0;
                    break;
            }
        }

        netTask();

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

public void netTask() {
    Global.mHandler = new Handler() {
        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Global.SUCCESS) {
                progressBar.setVisibility(View.GONE);
                String result = msg.getData().getString("result");
                System.out.println("Result:" + result);
                List<MActivityModel> activities= (JSON.parseObject(result, MActivityResponse.class)).getActivityModels();
                //填充内容
                activityListAdapter = new ActivityItemRecyclerViewAdapter(activities,getActivity(),getActivity().getBaseContext());
                recyclerView.setAdapter(activityListAdapter);
            }
        }
    };

    if(type == 0)
    NewHttpFactory.getActivityClient().listActivity();
    else{
        MActivityRequest mActivityRequest = new MActivityRequest();
        mActivityRequest.setType(type);
        NewHttpFactory.getActivityClient().listActivity(mActivityRequest);
    }

}


}
