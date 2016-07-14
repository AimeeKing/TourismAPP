package com.example.aimee.bottombar;

import android.content.Intent;
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
import com.example.aimee.bottombar.Adapter.TicketRecyclerViewAdapter;
import com.example.aimee.bottombar.newServer.model.MPackageModel;
import com.example.aimee.bottombar.newServer.request.MPackageRequest;
import com.example.aimee.bottombar.newServer.response.MPackageResponse;
import com.example.aimee.bottombar.newServer.utils.statics.Global;
import com.example.aimee.bottombar.newServer.utils.statics.NewHttpFactory;

import java.math.BigDecimal;
import java.util.List;
//这个界面是显示票子的,已经好了
public class TicketInfoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TicketRecyclerViewAdapter myrecycleAdapter;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private List<MPackageModel> packagesList;
    private String activityID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);
        initview();
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_content_moreinfo, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        else
//        {
//            if(id == R.id.submit)
//            {
//                Intent i = new Intent(TicketInfoActivity.this,BuySucessActivity.class);
//                startActivity(i);
//                finish();
//            }
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

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

    public void netTask() {
        Global.mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == Global.SUCCESS) {
                    progressBar.setVisibility(View.GONE);
                    String result = msg.getData().getString("result");
                    System.out.println("Result:" + result);
                    packagesList= (JSON.parseObject(result, MPackageResponse.class)).getPackageModels();
                    //填充内容
                    MPackageModel mPackageModel = new MPackageModel();
                    mPackageModel.setTitle("自建小组");
                    mPackageModel.setContent("客户自己组队");
                    mPackageModel.setPrice(new BigDecimal(20));
                    mPackageModel.setPkgName("自建小组");
                    mPackageModel.setRefActivityCode(activityID);
                    mPackageModel.setCode("0");
                    packagesList.add(mPackageModel);
                    myrecycleAdapter = new TicketRecyclerViewAdapter(TicketInfoActivity.this,TicketInfoActivity.this,packagesList);
                    recyclerView.setAdapter(myrecycleAdapter);
                }
            }
        };

        MPackageRequest mPackageRequest = new MPackageRequest();
        Intent i = getIntent();
        Bundle b=i.getExtras();
        activityID = b.getString("activity_id");
        mPackageRequest.setRefActivityCode(activityID);
        NewHttpFactory.getPackageClient().listPackage(mPackageRequest);
    }

}
