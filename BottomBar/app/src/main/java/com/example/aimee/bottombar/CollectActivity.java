package com.example.aimee.bottombar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.aimee.bottombar.Adapter.ActivityItemRecyclerViewAdapter;
import com.example.aimee.bottombar.newServer.model.MActivityModel;
import com.example.aimee.bottombar.newServer.model.ZanModel;
import com.example.aimee.bottombar.newServer.request.ZanRequest;
import com.example.aimee.bottombar.newServer.response.MActivityResponse;
import com.example.aimee.bottombar.newServer.response.ZanResponse;
import com.example.aimee.bottombar.newServer.utils.statics.Global;
import com.example.aimee.bottombar.newServer.utils.statics.NewHttpFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private TextView mTitle;
    private ActivityItemRecyclerViewAdapter activityListAdapter;
    private List<MActivityModel> activitiesList;
    private String userCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);
        SharedPreferences sh = getSharedPreferences("userinfo",
                Activity.MODE_PRIVATE);
        userCode = sh.getString("user_id","20160505221144000006");
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        //recyclerView = (RecyclerView) v.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar.setVisibility(View.VISIBLE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("我的收藏");
        netTask();
    }

    private void netTask() {
        Global.mHandler = new Handler() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == Global.SUCCESS) {
                    progressBar.setVisibility(View.GONE);
                    String result = msg.getData().getString("result");
                    System.out.println("Result:" + result);
                    activitiesList= (JSON.parseObject(result, MActivityResponse.class)).getActivityModels();
                    ZanRequest zanrequest = new ZanRequest();
                    zanrequest.setRefUserCode(userCode);
                    NewHttpFactory.getZanClient().listZan(zanrequest);
                }
            }
        };
        NewHttpFactory.getActivityClient().listActivity();
        Global.mHandler = new Handler() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == Global.SUCCESS) {
                    progressBar.setVisibility(View.GONE);
                    String result = msg.getData().getString("result");
                    System.out.println("Result:" + result);
                    List<ZanModel> zanList= (JSON.parseObject(result, ZanResponse.class)).getZanModels();
                    //填充内容
                    List<MActivityModel> activities = new ArrayList<>();
                    Set<String> activitycodelist = new HashSet();
                    for(int i = 0;i<zanList.size();i++)
                    {
                        activitycodelist.add(zanList.get(i).getRefObjectCode());
                    }
                    for(int i = 0;i<activitiesList.size();i++)
                    {
                        if(activitycodelist.contains(activitiesList.get(i).getCode()))
                        {
                            activities.add(activitiesList.get(i));
                        }
                    }
                    activityListAdapter = new ActivityItemRecyclerViewAdapter(activities,CollectActivity.this,CollectActivity.this,1);
                    recyclerView.setAdapter(activityListAdapter);
                }
            }
        };

    }
}
