package com.example.aimee.bottombar;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Aimee on 2016/3/20.
 */
//这个是一个备用界面，本来是想让它可以有一个ViewPager来展示两个界面（图片分享和圈子）
public class fragment_Topic extends Fragment {
    private Toolbar toolbar;
    private AppCompatActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_topic, container, false);
        toolbar= (Toolbar) v.findViewById(R.id.toolbar);
        activity=((AppCompatActivity) getActivity());
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        FragmentManager fm=((AppCompatActivity) getActivity()).getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.framelayout,new TopicFragment()).commit();

        return v;
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
