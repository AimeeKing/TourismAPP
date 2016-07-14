package com.example.aimee.bottombar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.cocosw.bottomsheet.BottomSheet;
import com.cocosw.bottomsheet.BottomSheetHelper;
import com.example.aimee.bottombar.Adapter.ContentRecyclerAdapter;
import com.example.aimee.bottombar.newServer.model.CommentModel;
import com.example.aimee.bottombar.newServer.model.MActivityModel;
import com.example.aimee.bottombar.newServer.request.CommentRequest;
import com.example.aimee.bottombar.newServer.request.ZanRequest;
import com.example.aimee.bottombar.newServer.response.CommentResponse;
import com.example.aimee.bottombar.newServer.response.CompanyResponse;
import com.example.aimee.bottombar.newServer.response.ZanResponse;
import com.example.aimee.bottombar.newServer.utils.Singleton;
import com.example.aimee.bottombar.newServer.utils.statics.Global;
import com.example.aimee.bottombar.newServer.utils.statics.NewHttpFactory;

import java.util.ArrayList;


//这是活动具体内容的界面,大概写好了
public class ContentActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private ImageView saveImg;
    private MActivityModel activity;
    private FloatingActionButton fab;
    private ArrayList<Object> arrayList = new ArrayList<>();
    private ContentRecyclerAdapter contentRecyclerAdapter;
    private EditText input;
    private ImageView headImg;
    private ZanResponse  zanResponse;
    private String userCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        SharedPreferences sh = getSharedPreferences("userinfo",
                Activity.MODE_PRIVATE);
        userCode = sh.getString("user_id","20160505221144000006");
        initView();
    }

    private BottomSheet.Builder getShareActions(String text) {
        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);

        return BottomSheetHelper.shareAction(this, shareIntent);
    }




    private void initView() {
        Intent i = getIntent();
        Bundle bundle=i.getExtras();
        activity= (MActivityModel) bundle.getSerializable("activity");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(activity.getTitle());
        headImg = (ImageView) findViewById(R.id.head_img);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setTag(R.drawable.cntentactivity_3);
        fab.setImageResource(R.drawable.cntentactivity_3);
        fab.setOnClickListener(this);
        saveImg = ((ImageView)findViewById(R.id.save_img));
        saveImg.setTag(R.drawable.savehalfblack);
        ImageRequest imageRequest = new ImageRequest(activity.getImage(), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                headImg.setImageBitmap(response);
            }
        },0,0, ImageView.ScaleType.CENTER_CROP,null,new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                headImg.setTag(R.drawable.savehalfblack);
            }
        });
        Singleton.getmInstance(getApplicationContext()).addToRequestQueue(imageRequest);
        LinearLayout buyLayout = (LinearLayout) findViewById(R.id.buy_layout);
        LinearLayout shareLayout = (LinearLayout) findViewById(R.id.share_layout);
        LinearLayout collectiveLayout = (LinearLayout) findViewById(R.id.collectivie_layout);
        LinearLayout commentLayout = (LinearLayout) findViewById(R.id.comment_layout);
        buyLayout.setOnClickListener(this);
        shareLayout.setOnClickListener(this);
        collectiveLayout.setOnClickListener(this);
        commentLayout.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.content);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getData();
        contentRecyclerAdapter = new ContentRecyclerAdapter(this,getBaseContext(),arrayList);
        recyclerView.setAdapter(contentRecyclerAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.collectivie_layout:
                if(saveImg.getTag().equals(R.drawable.savehalfblack)) {
                    saveImg.setImageResource(R.drawable.saveblack);
                    saveImg.setTag(R.drawable.saveblack);
                    Toast.makeText(ContentActivity.this,"收藏啦！",Toast.LENGTH_SHORT).show();
                    Global.mHandler=new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            String result= msg.getData().getString("result");
                            System.out.println("赞的结果"+result);
                            zanResponse = JSON.parseObject(result,ZanResponse.class);
                        }
                    };
                    ZanRequest zan = new ZanRequest();
                    zan.setRefUserCode(userCode);
                    zan.setRefObjectCode(activity.getCode());
                    zan.setUserCode(userCode);
                    zan.setObjTypeFlag(1);
                    NewHttpFactory.getZanClient().addZan(zan);
                }
                else
                {
                    saveImg.setImageResource(R.drawable.savehalfblack);
                    saveImg.setTag(R.drawable.savehalfblack);
                    Toast.makeText(ContentActivity.this,"您的收藏已取消！",Toast.LENGTH_SHORT).show();
                    Global.mHandler=new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            String result= msg.getData().getString("result");
                            System.out.println("赞的结果"+result);
                        }
                    };
                    ZanRequest zan = new ZanRequest();
                    zan.setRefUserCode(userCode);
                    zan.setRefObjectCode(activity.getCode());
                    zan.setUserCode(userCode);
                    zan.setCode(zanResponse.getCode());
                }
                break;
            case R.id.share_layout:
                BottomSheet sheet;
                sheet = getShareActions("分享好友").title("分享好友").limit(R.integer.no_limit).build();
                sheet.show();
                break;

            case R.id.comment_layout:
                //评价
                input =new EditText(ContentActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                lp.setMargins(10,2,10,2);
                input.setLayoutParams(lp);
                AlertDialog.Builder dialog = new AlertDialog.Builder(ContentActivity.this);
                dialog.setTitle("评论");
                dialog.setView(input);
                dialog.setIcon(R.drawable.comment_1);
                dialog.setPositiveButton("发布", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //input.getText().toString()
                        CommentRequest mcomment = new CommentRequest();
                        mcomment.setContent(input.getText().toString());
                        mcomment.setPublisherCode(userCode);
                        mcomment.setRefCode(activity.getCode());
                        mcomment.setType("活动");
                        CommentModel commentModel = new CommentModel();
                        commentModel.setContent(input.getText().toString());
                        commentModel.setPublisherCode(userCode);
                        commentModel.setRefCode(activity.getCode());
                        commentModel.setType("活动");
                        Global.mHandler=new Handler(){
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                String result= msg.getData().getString("result");
                                System.out.println("发布结果"+result);
                            }
                        };
                        NewHttpFactory.getCommentClient().addComment(mcomment);
                        arrayList.add(commentModel);
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                });
                dialog.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                dialog.show();
                break;
            case R.id.buy_layout:
                //项目
                Intent i1 = new Intent(ContentActivity.this,TicketInfoActivity.class);
                Bundle b1 = new Bundle();
                b1.putString("activity_id",activity.getCode());
                i1.putExtras(b1);
                startActivity(i1);
                break;
            case R.id.fab:
                if(fab.getTag().equals(R.drawable.cntentactivity_3)) {
                    fab.setImageResource(R.drawable.contentactivity_2);
                    fab.setTag(R.drawable.contentactivity_2);
                    Toast.makeText(ContentActivity.this,"好激动一个赞！",Toast.LENGTH_SHORT).show();
                    Global.mHandler=new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            String result= msg.getData().getString("result");
                            System.out.println("赞的结果"+result);
                            zanResponse = JSON.parseObject(result,ZanResponse.class);
                        }
                    };
                    ZanRequest zan = new ZanRequest();
                    zan.setRefUserCode(userCode);
                    zan.setRefObjectCode(activity.getCode());
                    zan.setUserCode(userCode);
                    zan.setObjTypeFlag(1);
                    NewHttpFactory.getZanClient().addZan(zan);
                }
                else
                {
                    fab.setImageResource(R.drawable.cntentactivity_3);
                    fab.setTag(R.drawable.cntentactivity_3);
                    Toast.makeText(ContentActivity.this,"您的赞已经取消",Toast.LENGTH_SHORT).show();
                    Global.mHandler=new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            String result= msg.getData().getString("result");
                            System.out.println("赞的结果"+result);
                        }
                    };
                    ZanRequest zan = new ZanRequest();
                    zan.setRefUserCode(userCode);
                    zan.setRefObjectCode(activity.getCode());
                    zan.setUserCode(userCode);
                    zan.setCode(zanResponse.getCode());
                    NewHttpFactory.getZanClient().delZan(zan);
                }
                break;
        }
    }

    public void nettask() {

        Global.mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == Global.SUCCESS) {
                    String result = msg.getData().getString("result");
                    System.out.println("Result:" + result);
                        CommentResponse commentResponse = JSON.parseObject(result, CommentResponse.class);
                    if (commentResponse.getCommentList() == null)
                        return;
                    for (int i = 5; i < 5 + commentResponse.getCommentList().size(); i++) {
                        arrayList.add(i, commentResponse.getCommentList().get(i - 5));
                    }
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        };
        String activityid= activity.getCode();
        CommentRequest mcommentRequest = new CommentRequest();
        mcommentRequest.setRefCode(activityid);
        NewHttpFactory.getCommentClient().listComment(mcommentRequest);
//        Global.mHandler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                if (msg.what == Global.SUCCESS) {
//                    String result = msg.getData().getString("result");
//                    System.out.println("Result:" + result);
//                    CompanyResponse Companyresponse = JSON.parseObject(result, CompanyResponse.class);
//                    arrayList.set(3,Companyresponse);
//                    recyclerView.getAdapter().notifyDataSetChanged();
//                }
//            }
//        };
    }
    private void getData() {
        arrayList.add(activity.getShortDesc());
//        "让电影《疯狂动物城》的正能量延续，让每一个孩子心中的英雄梦重现，开始一场冒险之旅！\n" +
//                "深入皇城根下，穿梭在胡同小巷，发现城市里不起眼的小角落，小惊喜。带上你的孩子，用发现美的火眼金睛开启一场探案传奇。\n" +
//                "与非物质文化遗产传承人面对面，倾听老北京风俗中的兔爷传奇，亲手绘制你心中的兔爷形象。"
        arrayList.add(activity);
        arrayList.add(2,"店铺");
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setContent("胡庆余堂");
        companyResponse.setCompanyName("胡庆余堂");
        arrayList.add(3,companyResponse);
        arrayList.add(4,"评论");
        nettask();
    }

}
