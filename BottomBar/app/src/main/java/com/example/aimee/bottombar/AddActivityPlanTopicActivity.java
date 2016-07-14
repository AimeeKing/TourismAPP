package com.example.aimee.bottombar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baoyz.actionsheet.ActionSheet;
import com.example.aimee.bottombar.LoadPic.GridViewAdapter;
import com.example.aimee.bottombar.LoadPic.PicassoImageLoader;
import com.example.aimee.bottombar.newServer.request.TopicRequest;
import com.example.aimee.bottombar.newServer.response.TopicResponse;
import com.example.aimee.bottombar.newServer.utils.statics.Global;
import com.example.aimee.bottombar.newServer.utils.statics.NewHttpFactory;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;


//这个界面用于家长添加活动需求,请求公司为他们定制服务，结果展示在话题界面中,已经改好
public class AddActivityPlanTopicActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    private final int REQUEST_CODE_EDIT = 1003;

    private List<PhotoInfo> mPhotoList;
    private GridView gridView;
    private GridViewAdapter gridViewAdapter;
    private Toolbar toolbar;
    private TextView datetext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);


        //这个是设置时间的
        datetext= (TextView) findViewById(R.id.datetext);
        datetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        AddActivityPlanTopicActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setThemeDark(false);
                dpd.vibrate(true);
                dpd.dismissOnPause(false);
                dpd.showYearPickerFirst(false);
                dpd.setAccentColor(Color.parseColor("#9C27B0"));
                dpd.setTitle("DatePicker Title");
                Calendar[] dates = new Calendar[13];
                for (int i = -6; i <= 6; i++) {
                    Calendar date = Calendar.getInstance();
                    date.add(Calendar.WEEK_OF_YEAR, i);
                    dates[i + 6] = date;
                }
                dpd.setHighlightedDays(dates);
                dpd.setAccentColor(getResources().getColor(R.color.colorPrimary));

                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
        initgrid();
    }

    void initgrid()
    {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mPhotoList = new ArrayList<>();
        gridView= (GridView) findViewById(R.id.gridView);
        gridViewAdapter=new GridViewAdapter(this, mPhotoList);
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ThemeConfig themeConfig = ThemeConfig.CYAN;

                FunctionConfig.Builder functionConfigBuilder = new FunctionConfig.Builder();
                cn.finalteam.galleryfinal.ImageLoader imageLoader;//加载方式，毕卡索
                imageLoader = new PicassoImageLoader();

                functionConfigBuilder.setEnableEdit(true)
                        .setEnableRotate(true)
                        .setEnableCrop(true)
                        .setEnableCamera(true)
                        .setEnablePreview(true).
                        setMutiSelectMaxSize(3)
                        .setCropReplaceSource(true);//可编辑,可旋转,可裁剪,显示相机,可预览,最大张数为9,修改后代替原图

                functionConfigBuilder.setSelected(mPhotoList);//添加过滤集合
                final FunctionConfig functionConfig = functionConfigBuilder.build();
                CoreConfig coreConfig = new CoreConfig.Builder(AddActivityPlanTopicActivity.this, imageLoader, themeConfig)
                        .setFunctionConfig(functionConfig)
                        .build();
                GalleryFinal.init(coreConfig);
                if (position == parent.getChildCount() - 1) {

                    //点击按钮后跳出的菜单
                    ActionSheet.createBuilder(AddActivityPlanTopicActivity.this, getSupportFragmentManager())
                            .setCancelButtonTitle("取消(Cancel)")
                            .setOtherButtonTitles("打开相册(Open Gallery)", "拍照(Camera)")
                            .setCancelableOnTouchOutside(true)
                            .setListener(new ActionSheet.ActionSheetListener() {
                                @Override
                                public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                                }

                                @Override
                                public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                                    switch (index) {
                                        case 0:
                                            GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
                                            break;
                                        case 1:
                                            GalleryFinal.openCamera(REQUEST_CODE_CAMERA, functionConfig, mOnHanlderResultCallback);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            })
                            .show();
                } else {
                    String path = mPhotoList.get(position).getPhotoPath();
                    if (new File(path).exists()) {
                        mPhotoList.remove(position);
                        GalleryFinal.openEdit(REQUEST_CODE_EDIT, functionConfig, path, mOnHanlderResultCallback);
                    } else {
                        Toast.makeText(AddActivityPlanTopicActivity.this, "图片不存在", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



        initImageLoader(this);
        initFresco();

    }

    private void initFresco() {
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setBitmapsConfig(Bitmap.Config.ARGB_8888)
                .build();
        Fresco.initialize(this, config);
    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                if(reqeustCode==REQUEST_CODE_GALLERY) {//区分是裁剪还是添加REQUEST_CODE_CAMERA
                    mPhotoList.clear();
                    mPhotoList.addAll(resultList);
                }
                else if(reqeustCode==REQUEST_CODE_EDIT||reqeustCode==REQUEST_CODE_CAMERA)
                {
                    mPhotoList.addAll(resultList);
                }
                gridViewAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(AddActivityPlanTopicActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };



    private void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
        ImageLoader.getInstance().init(config.build());
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_actplan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==R.id.submit)
        {
            //把得到的数据变成Topic
            TopicRequest activitiyTopicRequest=new TopicRequest();
            activitiyTopicRequest.setPublisherCode("");

            activitiyTopicRequest.setTitle("【活动定制】" + ((MaterialEditText) findViewById(R.id.title)).getText().toString());

            activitiyTopicRequest.setImage(Global.LocalHost+"img/miya.jpg");//这里还要改！！！！

            String content="活动原因："+((MaterialEditText) findViewById(R.id.reason)).getText().toString()
                            +"\n"+"活动描述"+((MaterialEditText) findViewById(R.id.request)).getText().toString();//request
            activitiyTopicRequest.setContent(content);
            //activitiy.set("快点来赞活动活动吧");
            Intent i=getIntent();
            Bundle b=new Bundle();
            b.putSerializable("TopicRequest", activitiyTopicRequest);
            i.putExtras(b);
            setResult(1001,i);

            //上传信息
            Global.mHandler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (msg.what == Global.SUCCESS) {
                        String result = msg.getData().getString("result");
                        System.out.println("Result:"+result);
                        TopicResponse topicResponse = JSON.parseObject(result,TopicResponse.class);
                        System.out.println("Result:"+JSON.toJSONString(topicResponse));
                    }
                }
            };
            NewHttpFactory.getTopicClient().addTopic(activitiyTopicRequest);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        datetext.setText(date);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=getIntent();
        Bundle b=new Bundle();
        b.putSerializable("activity", null);
        i.putExtras(b);
        setResult(1001,i);
        finish();
    }
}
