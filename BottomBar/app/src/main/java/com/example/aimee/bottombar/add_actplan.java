package com.example.aimee.bottombar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.actionsheet.ActionSheet;
import com.example.aimee.bottombar.LoadPic.GridViewAdapter;
import com.example.aimee.bottombar.LoadPic.PicassoImageLoader;
import com.example.aimee.bottombar.tony.po.Topic;
import com.example.aimee.bottombar.tony.utils.statics.Factories.HttpFactory;
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
import java.util.Date;
import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;

import static com.example.aimee.bottombar.tony.utils.statics.GsonUtil.Java2Json;


public class add_actplan extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    private final int REQUEST_CODE_EDIT = 1003;

    private List<PhotoInfo> mPhotoList;
    private GridView gridView;
    private GridViewAdapter gridViewAdapter;
    private Toolbar toolbar;
    private TextView datetext;
    private Handler mHandler;

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
                        add_actplan.this,
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
                dpd.setAccentColor(getResources().getColor(R.color.primary));

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
                CoreConfig coreConfig = new CoreConfig.Builder(add_actplan.this, imageLoader, themeConfig)
                        .setFunctionConfig(functionConfig)
                        .build();
                GalleryFinal.init(coreConfig);
                if (position == parent.getChildCount() - 1) {

                    //点击按钮后跳出的菜单
                    ActionSheet.createBuilder(add_actplan.this, getSupportFragmentManager())
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
                        Toast.makeText(add_actplan.this, "图片不存在", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(add_actplan.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };



    private void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
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
            //把得到的数据变成Activity
            Topic activitiy=new Topic();

            Date date=new Date();
            String activity_id=date.toString();
            activitiy.setTpc_id(activity_id);

            activitiy.setTitle("【活动定制】" + ((MaterialEditText) findViewById(R.id.title)).getText().toString());

            activitiy.setImage("http://imgsrc.baidu.com/forum/w%3D580/sign=f39802169b25bc312b5d01906ede8de7/e6a7a6af2edda3cca2b46ff803e93901203f922c.jpg");

            String content="活动原因："+((MaterialEditText) findViewById(R.id.reason)).getText().toString()
                            +"\n"+"活动描述"+((MaterialEditText) findViewById(R.id.request)).getText().toString();//request
            activitiy.setContent(content);
            activitiy.setPublisher_id("3C117DAE6CA0AD864FD4D58E0F647322");

            activitiy.setPub_time(activity_id);


            //activitiy.set("快点来赞活动活动吧");
            Intent i=getIntent();
            Bundle b=new Bundle();
            b.putSerializable("activity", activitiy);
            i.putExtras(b);
            setResult(1001,i);

            //上传信息
            mHandler=new Handler();
            String activity_json=Java2Json(activitiy);
            HttpFactory.getTopicClient(mHandler).addTopic(activity_json);

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
