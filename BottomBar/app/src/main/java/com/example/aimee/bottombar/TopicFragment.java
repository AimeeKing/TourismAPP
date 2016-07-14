package com.example.aimee.bottombar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.alibaba.fastjson.JSON;
import com.example.aimee.bottombar.Adapter.TopicRecyclerViewAdapter;
import com.example.aimee.bottombar.View.PullToRefreshView;
import com.example.aimee.bottombar.newServer.model.TopicModel;
import com.example.aimee.bottombar.newServer.request.TopicRequest;
import com.example.aimee.bottombar.newServer.response.TopicResponse;
import com.example.aimee.bottombar.newServer.utils.statics.Global;
import com.example.aimee.bottombar.newServer.utils.statics.NewHttpFactory;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.List;

//这个是第三个界面的话题界面,改好了
public class TopicFragment extends Fragment{
    private RecyclerView recyclerView;
    private TopicRecyclerViewAdapter myrecycleAdapter;
    private ProgressBar progressBar;
    private FloatingActionsMenu  mfab;
    private FloatingActionButton afab;
    private FloatingActionButton tfab;
    private List<TopicModel> topicList;
    private ArrayList<Integer> zan=new ArrayList<>();
    private PullToRefreshView mPullToRefreshView;
//    private ArrayList<String> writer=new ArrayList<>();

    private final int RESULT_TOPIC=1;
    private final int RESULT_ACTIVITY=2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        zan.add(5);
        zan.add(4);
        zan.add(3);
        zan.add(3);
        View v= inflater.inflate(R.layout.fragment_acttopic, container, false);
        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPullToRefreshView = (PullToRefreshView) v.findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, 800);
            }
        });
        progressBar.setVisibility(View.GONE);
//        progressBar.setVisibility(View.VISIBLE);
        mPullToRefreshView.setRefreshing(true);
        mfab= (FloatingActionsMenu) v.findViewById(R.id.multiple_actions);
        mfab.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(mfab.isExpanded()&&!hasFocus)
                {
                    mfab.expand();
                }
            }
        });
        afab = (FloatingActionButton) v.findViewById(R.id.add_activity);
        tfab = (FloatingActionButton) v.findViewById(R.id.add_topic);
        afab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),AddActivityPlanTopicActivity.class);
                startActivityForResult(i, RESULT_ACTIVITY);
            }
        });


        tfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),AddTopicActivity.class);
                startActivityForResult(i, RESULT_TOPIC);
            }
        });


        netTask();
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case RESULT_ACTIVITY:
                SharedPreferences shp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                Bundle b=data.getExtras();
                TopicModel topic= (TopicModel) b.getSerializable("activity");
                topicList.add(topic);
                myrecycleAdapter.notifyDataSetChanged();
                break;
            case RESULT_TOPIC:
                shp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                b=data.getExtras();
                topic= (TopicModel) b.getSerializable("activity");
                topicList.add(topic);
                myrecycleAdapter.notifyDataSetChanged();

        }


    }


    void netTask()
    {
        Global.mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == Global.SUCCESS) {
//                    progressBar.setVisibility(View.GONE);
                    mPullToRefreshView.setRefreshing(false);
                    String result = msg.getData().getString("result");
                    System.out.println("Result:"+result);
                    TopicResponse topicResponse = JSON.parseObject(result,TopicResponse.class);
                    System.out.println("Result:"+JSON.toJSONString(topicResponse));
                    topicList=topicResponse.getTopicModels();
                    //填充内容
                    myrecycleAdapter = new TopicRecyclerViewAdapter(getActivity(),getContext(),topicList,new Handler());
                    recyclerView.setAdapter(myrecycleAdapter);
                }
            }
        };


        //新建request 然后在request里面添加参数
        TopicRequest request = new TopicRequest();
        //工厂类获取Client对象 进行查询
        NewHttpFactory.getTopicClient().listTopic(request);

    }
}
