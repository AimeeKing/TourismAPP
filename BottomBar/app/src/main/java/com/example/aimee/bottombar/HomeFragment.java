package com.example.aimee.bottombar;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.aimee.bottombar.newServer.model.MActivityModel;
import com.example.aimee.bottombar.newServer.response.MActivityResponse;
import com.example.aimee.bottombar.newServer.utils.Singleton;
import com.example.aimee.bottombar.newServer.utils.statics.Global;
import com.example.aimee.bottombar.newServer.utils.statics.NewHttpFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aimee on 2016/3/19.
 */
//后期处理还有在下面放热门活动或者热门圈子内容
public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener,AdapterView.OnItemClickListener,View.OnClickListener{
    private static final int IMGNUM = 4;
    private LinearLayout linearLayout;//放点的集合
    private List<ImageView> img;//图片集合
    private ImageView dot[];//点集合
    private ViewPager viewPager;
    //   private List<String> path;//图片地址
//    private List<FeedItem> feedsList;
    private Context mContext;
    public HomeFragment(){
        mContext = Global.getmContext();
    }

    //bar
    private Spinner spinner;
    private List<String> cityList;
    private SpinnerAdapter adapter;
    //searchview
    private EditText searchview;
    private Toolbar toolbar;

    private LayoutInflater inflater;


    private LinearLayout layout;

    private ImageView histroyImg;//走进历史
    private ImageView scienceImg;//科学天地
    private ImageView VolunteerImg;//志愿活动
    private ImageView hikingImg;//周边出游
    private ImageView DIYImg;//手工DIY
    private ImageView knowledgeImg;//知识库


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater=inflater;
        View v= inflater.inflate(R.layout.fragment_home, container, false);
        initBar(v);
        initview(v);
        return v;
    }

private void progressActivity(List<MActivityModel> activityList) {
    try {
//        path=new ArrayList<>();
//        JSONArray posts = response.optJSONArray("imgs");
//        for (int i = 0; i < posts.length(); i++) {
//            String post = posts.optString(i);
//            System.out.println("imgs:"+post);
//            path.add(post);
//
//        }


        //放点
        dot = new ImageView[IMGNUM];
        for (int i = 0; i < IMGNUM; i++) {
            ImageView imgview = new ImageView(mContext);
            imgview.setLayoutParams(new ActionBar.LayoutParams(10, 10));
            dot[i] = imgview;
            if (i == 0) {
                //初始化的时候第0张图片被选择
                dot[i].setBackgroundResource(R.drawable.login_point);
            } else {
                dot[i].setBackgroundResource(R.drawable.login_point);
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
            linearLayout.addView(imgview, layoutParams);
        }


        img = new ArrayList<>();
        for (int i = 0; i < IMGNUM; i++) {
            ImageView imgview = new ImageView(mContext);
            imgview.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img.add(imgview);
            setPic(imgview, activityList.get(i).getImage());//装载图片
        }

        viewPager.setAdapter(new MyAdapter(img, mContext));

        viewPager.addOnPageChangeListener(HomeFragment.this);

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    private void initBar(View v)
    {
        toolbar =(Toolbar)v.findViewById(R.id.toolbar_activity);
      //  v.setSupportActionBar(toolbar);

        //spinner
        spinner= (Spinner) v.findViewById(R.id.spinner);
        cityList=new ArrayList<>();
        cityList.add("杭州");
        cityList.add("其他");
        adapter=new SpinnerAdapter();
        adapter.addItems(cityList);
        spinner.setAdapter(adapter);


        searchview = (EditText) v.findViewById(R.id.search_et_input);


        searchview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //此处为处理得到焦点时的情况
                    //跳转到新的界面
                    searchview.clearFocus();
                    Intent intent = new Intent(mContext,SearchActivity.class);//跳转到搜索界面
                    startActivity(intent);

                } else {
                    //失去焦点时的情况
                }
            }
        });
    }
    private void initview( View v) {
        netTask();
        linearLayout = (LinearLayout)v. findViewById(R.id.dots);
        viewPager = (ViewPager) v.findViewById(R.id.pager);
//
//        String url = Global.LocalHost+"servlet/imgs";//";//http://javatechig.com/?json=get_recent_posts&count=4
//        JsonObjectRequest jsObjRequest = new JsonObjectRequest
//                (Request.Method.POST, url, new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        progressActivity(response);
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // TODO Auto-generated method stub
//                    }
//                });
//
//
//        Singleton.getmInstance(mContext.getApplicationContext()).addToRequestQueue(jsObjRequest);
//

        layout= (LinearLayout) v.findViewById(R.id.buttons);
        histroyImg = (ImageView)(layout.findViewById(R.id.img_1_1));
        scienceImg = (ImageView)( layout.findViewById(R.id.img_2_1));
        VolunteerImg = (ImageView) (layout.findViewById(R.id.img_3_1));
        hikingImg = (ImageView) (layout.findViewById(R.id.img_1_2));
        DIYImg = (ImageView) (layout.findViewById(R.id.img_2_2));
        knowledgeImg = (ImageView) (layout.findViewById(R.id.img_3_2));

        histroyImg.setTag("走进历史");
        scienceImg.setTag("科学天地");
        VolunteerImg.setTag("志愿活动");
        hikingImg.setTag("周边出游");
        DIYImg.setTag("手工DIY");
        knowledgeImg.setTag("知识库");


        histroyImg.setOnClickListener(this);
        scienceImg.setOnClickListener(this);
        VolunteerImg.setOnClickListener(this);
        hikingImg.setOnClickListener(this);
        DIYImg.setOnClickListener(this);
        knowledgeImg.setOnClickListener(this);
    }

    private void setPic(final ImageView imgview, String s) {
        //装载图片
        //通过毕加索来加载图片，其实用volley也可以
      /*  Picasso.with(getBaseContext()).load(s)
                .error(R.drawable.mengmeizi)
                .placeholder(R.drawable.mengmeizi)
                .into(imgview);
                */
        ImageRequest request = new ImageRequest(s,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        imgview.setImageBitmap(bitmap);
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        imgview.setImageResource(R.drawable.mengmeizi);
                    }
                });


        Singleton.getmInstance(mContext.getApplicationContext()).addToRequestQueue(request, "pics");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //当按下spinner下的按钮时的反应
        switch (position){
            case 0:
                Toast.makeText(mContext, "你按了" + cityList.get(0), Toast.LENGTH_LONG);
                break;
            case 1:
                Toast.makeText(mContext,"你按了"+cityList.get(1),Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onClick(View v) {//按下按钮
        if(!v.getTag().equals("知识库")) {
            Intent i = new Intent(getActivity(), ClassifyActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("key", v.getTag().toString());//最好留一下這個是第幾個按鈕按下的
            i.putExtras(bundle);
            startActivity(i);
        }
        else
        {
            Intent i = new Intent(getActivity(), KnowledgeSwipeStake.class);
            startActivity(i);
        }
    }

    public class SpinnerAdapter extends BaseAdapter {
        private List<String> mItem = new ArrayList<>();
        public int getCount() {
            return mItem.size();
        }

        @Override
        public Object getItem(int position) {
            return mItem.get(position);
        }

        public void clear()
        {
            mItem.clear();
        }

        public void addItem(String item)
        {
            mItem.add(item);
        }

        private void addItems(List<String> items)
        {
            mItem.addAll(items);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            if(convertView==null||!convertView.getTag().toString().equals("DROPDOWN"))
            {
                convertView=inflater.inflate(R.layout.spinner_dropdown_item, parent, false);
                convertView.setTag("DROPDOWN");
            }
            TextView tv= (TextView) convertView.findViewById(R.id.spiner_item);
            tv.setText(getTitle(position));
            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView ==null||!convertView.getTag().toString().equals("NON_DROPDOWN"))
            {
                convertView = inflater.inflate(R.layout.
                        spinner_item, parent, false);
                convertView.setTag("NON_DROPDOWN");
            }
            TextView textView = (TextView) convertView.findViewById(R.id.spinnertitle);
            textView.setText(getTitle(position));

            return convertView;
        }





        private  String getTitle(int position)
        {

            return position>=0&&position<mItem.size()?mItem.get(position):"杭州";
        }
    }







    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int arg0) {
        for (int i = 0; i < dot.length; i++) {
            if (arg0 == i) {
                dot[i].setImageResource(R.drawable.login_point_selected);
            } else {
                dot[i].setImageResource(R.drawable.login_point);
            }
            if(arg0 !=0)
                dot[0].setImageResource(R.drawable.login_point);

        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private class MyAdapter extends PagerAdapter {
        private List<ImageView> imageViews;
        private Context context;

        public MyAdapter(List<ImageView> imageViews, Context context) {
            this.imageViews = imageViews;
            this.context = context;
        }

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(imageViews.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(imageViews.get(position));
            return imageViews.get(position);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void netTask() {
        Global.mHandler = new Handler() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                    String result = msg.getData().getString("result");
                    System.out.println("Result:" + result);
                    List<MActivityModel> activities= (JSON.parseObject(result, MActivityResponse.class)).getActivityModels();
                    //填充内容
                progressActivity(activities);
                }
            };
        NewHttpFactory.getActivityClient().listActivity();
        }

}
