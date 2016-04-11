package com.example.aimee.bottombar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.aimee.bottombar.tony.po.Packages;
import com.example.aimee.bottombar.tony.utils.statics.Factories.HttpFactory;
import com.example.aimee.bottombar.tony.utils.statics.Global;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.List;

import static com.example.aimee.bottombar.tony.utils.statics.GsonUtil.JsonArray2JavaList;

public class content_moreinfo extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyrecycleAdapter myrecycleAdapter;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private List<Packages> packagesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);
        initview();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_content_moreinfo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initview() {
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar.setVisibility(View.VISIBLE);



        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("门票");

        nettask();

    }

    public class MyrecycleAdapter extends  RecyclerView.Adapter<MyrecycleAdapter.CustomViewHolder>{
        private List<Packages> packagesList;
        private Context mContext;

        public MyrecycleAdapter(Context context,List<Packages> packagesList)
        {
            mContext=context;
            this.packagesList=packagesList;
        }


        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewitem,null);
            CustomViewHolder viewHolder = new CustomViewHolder(view);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position) {
            final Packages  packages = packagesList.get(position);

            //download image using picasso library



            holder.title.setText(Html.fromHtml(packages.getTitle()));
         //   holder.PublishTime.setText("发布时间：" + packages.getPub_time());
            holder.price.setText("现价只要：" + packages.getPrice());
            // holder.moreinfo.setText(packages.getContent());
            //handle click event on both title and image click
            holder.book.setTag(holder);
            holder.book2.setTag(holder);
            holder.moreinfo.setTag(holder);

            final String text = packages.getContent();

            holder.moreinfo.setOnExpandStateChangeListener(new ExpandableTextView.OnExpandStateChangeListener() {
                @Override
                public void onExpandStateChanged(TextView textView, boolean b) {
                    if(b)//Expanded
                    {

                        textView.setText(text);
                    }
                    else
                        textView.setText("详细信息\n\n\n\n\n\n\n");
                }
            });

            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()){

                    }
                }
            };
            holder.book.setOnClickListener(clickListener);
            holder.book2.setOnClickListener(clickListener);

        }

        @Override
        public int getItemCount() {
            return (null != packagesList ? packagesList.size() : 0);
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            protected TextView title;
            protected TextView price;
            protected TextView PublishTime;
            protected Button book;
            protected Button book2;
            protected ExpandableTextView moreinfo;

            public CustomViewHolder(View view) {
                super(view);
                this.title = (TextView) view.findViewById(R.id.title);
                this.price = (TextView) view.findViewById(R.id.prise);
                this.PublishTime = (TextView) view.findViewById(R.id.publishtime);

                this.book = (Button) view.findViewById(R.id.buy);
                this.book2 = (Button) view.findViewById(R.id.buyit);
                this.moreinfo = (ExpandableTextView) view.findViewById(R.id.expand_text_view);
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
                    packagesList=JsonArray2JavaList(result, Packages.class);
                    //填充内容


                    myrecycleAdapter = new MyrecycleAdapter(content_moreinfo.this,packagesList);
                    recyclerView.setAdapter(myrecycleAdapter);
                }
            }
        };

        Intent i = getIntent();
        Bundle b=i.getExtras();
        String activity_id=b.getString("activity_id");
        HttpFactory.getPkgClient(mHandler).getPkg(activity_id);
    }

}
