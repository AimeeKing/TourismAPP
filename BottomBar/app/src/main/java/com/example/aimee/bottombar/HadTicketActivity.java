package com.example.aimee.bottombar;

import android.app.Activity;
import android.content.SharedPreferences;
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
import com.example.aimee.bottombar.Adapter.TicketHadAdapter;
import com.example.aimee.bottombar.newServer.model.PurchaseModel;
import com.example.aimee.bottombar.newServer.request.PurchaseRequest;
import com.example.aimee.bottombar.newServer.response.PurchaseResponse;
import com.example.aimee.bottombar.newServer.utils.statics.Global;
import com.example.aimee.bottombar.newServer.utils.statics.NewHttpFactory;

import java.util.List;

public class HadTicketActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TicketHadAdapter myrecycleAdapter;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private String userCode;
    private List<PurchaseModel> packagesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);
        SharedPreferences sh = getSharedPreferences("userinfo",
                Activity.MODE_PRIVATE);
        userCode = sh.getString("user_id","20160505221144000006");
        initview();
    }

    private void initview() {
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar.setVisibility(View.VISIBLE);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("门票");

        netTask();

    }

    private void netTask() {
        Global.mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == Global.SUCCESS) {
                    progressBar.setVisibility(View.GONE);
                    String result = msg.getData().getString("result");
                    System.out.println("Result:" + result);
                    packagesList= (JSON.parseObject(result, PurchaseResponse.class)).getPurchaseModels();
                    if(packagesList != null) {
                        myrecycleAdapter = new TicketHadAdapter(packagesList, HadTicketActivity.this, HadTicketActivity.this);
                        recyclerView.setAdapter(myrecycleAdapter);
                    }
                }
            }
        };

        PurchaseRequest purchaseRequest = new PurchaseRequest();
        purchaseRequest.setRefUserCode(userCode);
        NewHttpFactory.getPurchaseClient().listPurchase(purchaseRequest);
    }
}
