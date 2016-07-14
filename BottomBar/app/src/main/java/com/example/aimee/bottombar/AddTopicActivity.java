package com.example.aimee.bottombar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.baoyz.actionsheet.ActionSheet;
import com.example.aimee.bottombar.LoadPic.GridViewAdapter;
import com.example.aimee.bottombar.LoadPic.PicassoImageLoader;
import com.example.aimee.bottombar.newServer.request.TopicRequest;
import com.example.aimee.bottombar.newServer.utils.statics.Global;
import com.example.aimee.bottombar.newServer.utils.statics.NewHttpFactory;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;
//这个界面是家长发一些类似QQ动态的内容，结果返回到Topic界面
public class AddTopicActivity extends AppCompatActivity {
    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    private final int REQUEST_CODE_EDIT = 1003;
    private String userCode;
    private List<PhotoInfo> mPhotoList;
    private GridView gridView;
    private GridViewAdapter gridViewAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_topic_activity);
        SharedPreferences sh = getSharedPreferences("userinfo",
                Activity.MODE_PRIVATE);
        userCode = sh.getString("user_id","20160505221144000006");
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回按钮
        getSupportActionBar().setDisplayShowHomeEnabled(false);//不显示图标
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
                        setMutiSelectMaxSize(9)
                        .setCropReplaceSource(true);//可编辑,可旋转,可裁剪,显示相机,可预览,最大张数为9,修改后代替原图

                functionConfigBuilder.setSelected(mPhotoList);//添加过滤集合
                final FunctionConfig functionConfig = functionConfigBuilder.build();
                CoreConfig coreConfig = new CoreConfig.Builder(AddTopicActivity.this, imageLoader, themeConfig)
                        .setFunctionConfig(functionConfig)
                        .build();
                GalleryFinal.init(coreConfig);
                if (position == parent.getChildCount() - 1) {

                    //点击按钮后跳出的菜单
                    ActionSheet.createBuilder(AddTopicActivity.this, getSupportFragmentManager())
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
                        Toast.makeText(AddTopicActivity.this, "图片不存在", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(AddTopicActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
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

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.submit)
        {
            //把得到的数据变成Topic
            TopicRequest activitiyTopicRequest=new TopicRequest();
            activitiyTopicRequest.setPublisherCode(userCode);

            activitiyTopicRequest.setTitle(((EditText)findViewById(R.id.title)).getText().toString());

            activitiyTopicRequest.setImage(Global.LocalHost+"img/girl.jpg");//这里还要改！！！！

            String content=((EditText) findViewById(R.id.text)).getText().toString();//request
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
                    }
                }
            };
            NewHttpFactory.getTopicClient().addTopic(activitiyTopicRequest);
            finish();
        }
        return super.onOptionsItemSelected(item);
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
