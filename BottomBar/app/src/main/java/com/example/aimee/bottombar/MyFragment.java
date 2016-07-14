package com.example.aimee.bottombar;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.cocosw.bottomsheet.BottomSheet;
import com.example.aimee.bottombar.LoadPic.PicassoImageLoader;
import com.example.aimee.bottombar.Volley_util.UploadUtils;
import com.example.aimee.bottombar.newServer.utils.Singleton;
import com.example.aimee.bottombar.newServer.utils.statics.Global;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Aimee on 2016/3/20.
 */
//暂时不改,还要加的内容有存储信息提取信息啊什么的
public class MyFragment extends Fragment {
    private static final int RESULT_SETTING =0001 ;
    private static final int RESULT_LOGIN = 0002;
    private Context mContext;
    private final int REQUEST_CODE_GALLERY = 1001;

    private Handler handler;

    private ListView listview;
    private SimpleAdapter adapter;
    private String item_name[]={"全部订单","卡券","收藏","计划","分享好友","设置"};
    private int img_list[]={R.drawable.mypay,R.drawable.card
            ,R.drawable.collective,R.drawable.myplan,R.drawable.sharefriends,R.drawable.setting};
    private List<PhotoInfo> mPhotoList;

    private CircleImageView imageview;
    private TextView textuser;
    private Boolean login=false;
    private SharedPreferences sh;
    public String str;//用在传递名字
    private String imgurl;
    private String userName;
    private String password;

    public MyFragment(){
        this.mContext = Global.mContext;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_my, container, false);
        sh = getActivity().getSharedPreferences("userinfo",
                Activity.MODE_PRIVATE);
        str = sh.getString("user_nickname","");
        userName = sh.getString("user_name","");
        password = sh.getString("password","");
        imgurl = sh.getString("imgurl","http://hbimg.b0.upaiyun.com/80bc9bfbedf758c1e14f62debd8f2b3880462059432a-sOk5tJ_fw658");
        if(str.equals(""))
            login = false;
        else
            login =true;
        handler = new Handler();
        initView(v);
        return v;
    }

    private void initView(View v) {
        listview = (ListView)v.findViewById(R.id.menu);
        adapter = new SimpleAdapter(mContext,getData(),
                R.layout.listview,new String[]{"title","img"},new int[]{R.id.item_name,R.id.img});
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(5==position)//如果按了设置
                {
                    Intent i=new Intent();
                    i.setClass(getActivity(),SettingActivity.class);
                    String username=textuser.getText().toString();
                    Bundle bundle=new Bundle();
                    bundle.putString("user_nickname",username);
                    i.putExtras(bundle);
                    startActivityForResult(i,0);
                }
                else
                    if(3 == position)
                    {
                        Intent i = new Intent();
                        i.setClass(getActivity(),PlanActivity.class);
                        startActivity(i);
                    }
                else
                         if(2 == position)
                        {
                            //点击了收藏按钮
                            Intent i = new Intent();
                            i.setClass(getActivity(),CollectActivity.class);
                            startActivity(i);
                        }
                else
                        if(1 == position)
                        {
                            //点击了卡券
                            Intent i = new Intent();
                            i.setClass(getActivity(),CardActivity.class);
                            startActivity(i);
                        }
                else
                        if(0 == position)
                        {
                            //点击了订单
                            Intent i = new Intent();
                            i.setClass(getActivity(),HadTicketActivity.class);
                            startActivity(i);
                        }
            }
        });



        mPhotoList = new ArrayList<>();

        imageview= (CircleImageView) v.findViewById(R.id.profile_image);
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里要做的事情,调用库,更改图片，上传图片
                System.out.println("在我的界面"+"点了改图片");

                ThemeConfig themeConfig = ThemeConfig.CYAN;

                FunctionConfig.Builder functionConfigBuilder = new FunctionConfig.Builder();
                cn.finalteam.galleryfinal.ImageLoader imageLoader;//加载方式，毕卡索
                imageLoader = new PicassoImageLoader();

                functionConfigBuilder.setEnableEdit(true)
                        .setEnableRotate(true)
                        .setEnableCrop(true)
                        .setEnableCamera(true)
                        .setEnablePreview(true).
                        setMutiSelectMaxSize(1)
                        .setCropReplaceSource(true);//可编辑,可旋转,可裁剪,显示相机,可预览,最大张数为1,修改后代替原图

                functionConfigBuilder.setSelected(mPhotoList);//添加过滤集合
                final FunctionConfig functionConfig = functionConfigBuilder.build();
                CoreConfig coreConfig = new CoreConfig.Builder(getActivity().getBaseContext(), imageLoader, themeConfig)
                        .setFunctionConfig(functionConfig)
                        .build();
                GalleryFinal.init(coreConfig);

                GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);


            }


        });

        textuser= (TextView) v.findViewById(R.id.text_in);

        if(isLogin())
        {
            //设置为他的名字
            sh.getString("user_nickname","请先登录");
            textuser.setText(sh.getString("user_nickname","请先登录"));
            ImageRequest imageRequest =new ImageRequest(imgurl, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    imageview.setImageBitmap(response);
                }
            }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    imageview.setImageResource(R.drawable.ribbitjpeg);
                }
            });
            Singleton.getmInstance(getActivity().getBaseContext().getApplicationContext()).addToRequestQueue(imageRequest);
        }
        else
        {
            imageview.setImageResource(R.drawable.ribbitjpeg);
            textuser.setText("请先登录");
        }

        textuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin()) {
                    //点击后登出
                    new BottomSheet.Builder(getActivity()).title("确定登出？").sheet(R.menu.menu_out).listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case R.id.get_out:
                                textuser.setText("请先登录");
                                login = false;
                                break;
                        }
                    }
                }).show();
                } else {

                    /*Intent i = new Intent(getActivity(), Sign_Activity.class);
                    startActivity(i);

                    textuser.setText("username");
                    */
                    new BottomSheet.Builder(getActivity()).title("").sheet(R.menu.menu_login).listener(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i;
                            Bundle bundle;
                            switch (which)
                            {
                                case R.id.sign:
                                    i=new Intent();
                                    i.setClass(getActivity(),SinUpActivitySec.class);
                                    bundle=new Bundle();
                                    bundle.putBoolean("isMy",true);
                                    i.putExtras(bundle);
                                    startActivityForResult(i,1);
                                    break;
                                case R.id.login:
                                    i =new Intent();
                                    i.setClass(getActivity(),LoginFragment.class);
                                    bundle=new Bundle();
                                    bundle.putBoolean("isMy",true);
                                    i.putExtras(bundle);
                                    startActivityForResult(i,1);
                            }
                        }
                    }).show();
//                    Intent i=new Intent();
//                    i.setClass(getActivity(),SinUpActivitySec.class);
////                    String username=textuser.getText().toString();
//                    Bundle bundle=new Bundle();
//                    bundle.putBoolean("isMy",true);
//                    i.putExtras(bundle);
//                    startActivityForResult(i,1);
//                    login = true;
                }
            }
        });

        initFresco();
        initImageLoader(getActivity().getBaseContext());
    }

    //设置回调函数
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        str=null;
        switch (resultCode)
        {
            case RESULT_SETTING:
                Bundle b=data.getExtras();//b是从setting中传回来的Intent
                str=b.getString("user_nickname");
                if(str != null)
                    login = true;
                else
                    str = "请先登录";
                break;
            case RESULT_LOGIN:
                b = data.getExtras();
                str=b.getString("user_nickname");
                if(str != null)
                    login = true;
                else
                str = "请先登录";
                break;
        }
        textuser.setText(str);
    }


    private Boolean isLogin()//是否登录
    {
        return login;
    }


    private List<Map<String,Object>> getData()
    {
        List<Map<String,Object>> list = new ArrayList<>();
        for(int i=0;i<item_name.length;i++)
        {
            Map<String,Object> map=new HashMap<>();
            map.put("title",item_name[i]);
            map.put("img",img_list[i]);
            list.add(map);
        }
        return list;
    }



    private void initFresco() {
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(getActivity().getBaseContext())
                .setBitmapsConfig(Bitmap.Config.ARGB_8888)
                .build();
        Fresco.initialize(getActivity().getBaseContext(), config);
    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, final List<PhotoInfo> resultList) {
            if (resultList != null) {
                if(reqeustCode==REQUEST_CODE_GALLERY) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    DisplayImageOptions options = new DisplayImageOptions.Builder()
                                            .showImageOnFail(R.drawable.ic_gf_default_photo)
                                            .showImageForEmptyUri(R.drawable.ic_gf_default_photo)
                                            .showImageOnLoading(R.drawable.ic_gf_default_photo).build();
                                    PhotoInfo photoInfo = resultList.get(0);
                                    ImageLoader.getInstance().displayImage("file:/" + photoInfo.getPhotoPath(), imageview, options);
                                    UploadUtils.uploadFile("file:/" + photoInfo.getPhotoPath(),userName,password);
                                }
                            });
                        }
                    }).start();
                }
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(getActivity().getBaseContext(), errorMsg, Toast.LENGTH_SHORT).show();
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
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
