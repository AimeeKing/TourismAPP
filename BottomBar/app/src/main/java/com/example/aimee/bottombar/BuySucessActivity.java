package com.example.aimee.bottombar;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BuySucessActivity extends AppCompatActivity {
   private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_sucess);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("下单成功");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.wait);
        final RelativeLayout showlayout = (RelativeLayout) findViewById(R.id.showlayout);
        TextView createTime = (TextView) findViewById(R.id.create_time_tv);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        createTime.setText(df.format(new Date()));
        Runnable runnable =new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                showlayout.setVisibility(View.VISIBLE);
            }
        };
        new Handler().postDelayed(runnable,3000);
    }
}
