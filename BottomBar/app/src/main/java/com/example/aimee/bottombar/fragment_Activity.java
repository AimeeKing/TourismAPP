package com.example.aimee.bottombar;

import android.app.ActivityOptions;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.aimee.bottombar.tony.po.Activities;
import com.example.aimee.bottombar.tony.utils.statics.Factories.HttpFactory;
import com.example.aimee.bottombar.tony.utils.statics.Global;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.aimee.bottombar.tony.utils.statics.GsonUtil.JsonArray2JavaList;

/**
 * Created by Aimee on 2016/3/20.
 */
public class fragment_Activity extends Fragment{
    private RecyclerView recyclerView;
    private MyrecycleAdapter myrecycleAdapter;
    private ProgressBar progressBar;
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_activity, container, false);
        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar.setVisibility(View.VISIBLE);



        toolbar= (Toolbar) v.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        if(getActivity().getTitle().equals("tab_view")) {
            mTitle.setText("最新活动");
        }
        else
        {
            Intent i=getActivity().getIntent();
            String title=i.getExtras().getString("key");
            mTitle.setText(title);
        }

        /*下载数据通过URL*/
       // url = "http://javatechig.com/?json=get_recent_posts&count=45";
       // new AsyncHttpTask().execute(url);


        nettask();

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


    public class MyrecycleAdapter extends  RecyclerView.Adapter<MyrecycleAdapter.CustomViewHolder>{
        private List<Activities> activitiesList;
        private Context mContext;

        public MyrecycleAdapter(Context context,List<Activities> activities)
        {
            mContext=context;
            activitiesList=activities;
        }


        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_big,null);
            CustomViewHolder viewHolder = new CustomViewHolder(view);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position) {
            final Activities activity = activitiesList.get(position);

            //download image using picasso library
            Picasso.with(mContext).load(activity.getImage())
                    .error(R.drawable.mengmeizi)
                    .placeholder(R.drawable.mengmeizi)
                    .into(holder.imageView);


            holder.textView.setText(Html.fromHtml(activity.getTitle()));
            holder.context.setText("地点位于："+activity.getDestination());

            //handle click event on both title and image click
            holder.textView.setTag(holder);
            holder.imageView.setTag(holder);



            View.OnClickListener clickListener = new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    CustomViewHolder holder = (CustomViewHolder) v.getTag();
                    int position = holder.getAdapterPosition();
                    Activities activity = activitiesList.get(position);
                    // Toast.makeText(mContext, feedItem1.getTitle(), Toast.LENGTH_LONG).show();
                    //  Toast.makeText(mContext,feedItem1.getTitle(),Toast.LENGTH_LONG).show();
                    // int id = feedItem1.getId();
                    //然后把id给新的activity,新的activity利用id去查文章
                    Intent i =new Intent(getActivity(), content_activity.class);

                    View sharedView = holder.imageView;//feedItem1.getThumbnail()
                    String transitionName ="tran_img";
                    ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(),sharedView,transitionName);
                    Bundle bundle=transitionActivityOptions.toBundle();
                    Bundle bundle2=new Bundle();
                    bundle2.putSerializable("activity",activity);
                    i.putExtras(bundle2);
                    startActivity(i, bundle);
                }
            };

            holder.imageView.setOnClickListener(clickListener);
            holder.textView.setOnClickListener(clickListener);

        }

        @Override
        public int getItemCount() {
            return (null != activitiesList ? activitiesList.size() : 0);
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            protected ImageView imageView;
            protected TextView textView;
            protected TextView context;

            public CustomViewHolder(View view) {
                super(view);
                this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
                this.textView = (TextView) view.findViewById(R.id.title);
                this.context = (TextView) view.findViewById(R.id.context);
            }
        }
    }
public void nettask() {
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Global.SUCCESS) {
                progressBar.setVisibility(View.GONE);
                String result = msg.getData().getString("result");
                System.out.println("Result:" + result);
                ArrayList<Activities> activities=JsonArray2JavaList(result, Activities.class);
                //填充内容


                myrecycleAdapter = new MyrecycleAdapter(getActivity(),activities);
                recyclerView.setAdapter(myrecycleAdapter);
            }
        }
    };


    HttpFactory.getActClient(mHandler).getAct();
}


}
