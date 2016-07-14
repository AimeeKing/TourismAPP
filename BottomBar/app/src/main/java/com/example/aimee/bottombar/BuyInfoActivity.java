package com.example.aimee.bottombar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.aimee.bottombar.newServer.model.MPackageModel;
import com.example.aimee.bottombar.newServer.request.PurchaseRequest;
import com.example.aimee.bottombar.newServer.utils.statics.Global;
import com.example.aimee.bottombar.newServer.utils.statics.NewHttpFactory;
import com.rengwuxian.materialedittext.MaterialEditText;

public class BuyInfoActivity extends AppCompatActivity {
    private MaterialEditText childNumEdit;
    private MPackageModel mPackageModel;
    private MaterialEditText requestEdit;
    private String userCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_info);
        SharedPreferences sh = getSharedPreferences("userinfo",
                Activity.MODE_PRIVATE);
        userCode = sh.getString("user_id","20160505221144000006");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("确认订单");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        childNumEdit = (MaterialEditText) findViewById(R.id.child_num);
        requestEdit = (MaterialEditText) findViewById(R.id.request);
        Intent intentFromTicketInfo = getIntent();
        mPackageModel = (MPackageModel) intentFromTicketInfo.getExtras().getSerializable("package");
        if(intentFromTicketInfo.getExtras().getInt("type") == 1)
           System.out.println("这个是自己创建界面");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_content_moreinfo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        else
        {
            if(id == R.id.submit)
            {
                PurchaseRequest purchaseRequest = new PurchaseRequest();
                purchaseRequest.setUserCode(userCode);
                purchaseRequest.setRefUserCode(userCode);
               // purchaseRequest.setPurchaseDate(new java.util.Date());
                purchaseRequest.setCount(Integer.parseInt(childNumEdit.getText().toString()));
                purchaseRequest.setStatus("未完成");
                purchaseRequest.setRateStatus("NO");
                purchaseRequest.setRefPackageCode(mPackageModel.getCode());
                purchaseRequest.setContent(requestEdit.getText().toString());

                Global.mHandler=new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        String result= msg.getData().getString("result");
                        System.out.println("买票的结果"+result);
                        Intent i = new Intent(BuyInfoActivity.this,BuySucessActivity.class);
                        startActivity(i);
                        finish();
                    }
                };
                NewHttpFactory.getPurchaseClient().addPurchase(purchaseRequest);
//                Intent i = new Intent(BuyInfoActivity.this,BuySucessActivity.class);
//                startActivity(i);
//                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }



}
