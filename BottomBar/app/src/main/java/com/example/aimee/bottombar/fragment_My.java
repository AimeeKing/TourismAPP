package com.example.aimee.bottombar;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Aimee on 2016/3/20.
 */
public class fragment_My extends Fragment {
    private Context mContext;
    public fragment_My(Context context){
        mContext = context;
    }
    private ListView listview;
    private SimpleAdapter adapter;
    private String item_name[]={"全部订单","卡券","收藏","计划","分享好友","设置"};
    private int img_list[]={R.drawable.mypay,R.drawable.card
            ,R.drawable.collective,R.drawable.myplan,R.drawable.sharefriends,R.drawable.setting};


    private CircleImageView imageview;
    private TextView textuser;
    private Boolean login=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_my, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        listview = (ListView)v.findViewById(R.id.menu);

        adapter = new SimpleAdapter(mContext,getData(),
                R.layout.listview,new String[]{"title","img"},new int[]{R.id.item_name,R.id.img});
        listview.setAdapter(adapter);

        imageview= (CircleImageView) v.findViewById(R.id.profile_image);
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里要做的事情,调用库,更改图片，上传图片
                Toast.makeText(mContext,"点了改图片",Toast.LENGTH_SHORT).show();
            }
        });

        textuser= (TextView) v.findViewById(R.id.text_in);

        if(isLogin())
        {
            //设置为他的名字
            textuser.setText("username");
        }
        else
        {
            textuser.setText("请先登录");
        }

        textuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLogin())
                {
                    //点击后登出
                    textuser.setText("请先登录");
                    login=false;
                }
                else
                {

                    Intent i=new Intent(getActivity(),Sign_Activity.class);
                    startActivity(i);

                    textuser.setText("username");
                    login=true;
                }
            }
        });


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




    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
