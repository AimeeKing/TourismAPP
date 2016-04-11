package com.example.aimee.bottombar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.aimee.bottombar.tony.po.Comment;
import com.example.aimee.bottombar.tony.po.User;
import com.example.aimee.bottombar.tony.utils.statics.Factories.HttpFactory;
import com.example.aimee.bottombar.tony.utils.statics.Global;
import com.example.aimee.bottombar.tony.utils.statics.GsonUtil;
import com.loopj.android.http.RequestParams;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.aimee.bottombar.tony.utils.statics.GsonUtil.Json2Java;
import static com.example.aimee.bottombar.tony.utils.statics.GsonUtil.JsonArray2JavaList;

public class Comment_activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyrecycleAdapter myrecycleAdapter;
    private Button submit;
    private MaterialEditText editText;
    private List<Comment> commentsList;
    private Handler handler2;

    private String activityid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_activity);

        submit= (Button) findViewById(R.id.submit);
        editText = (MaterialEditText)findViewById(R.id.comment);
        recyclerView = (RecyclerView)findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        nettask();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comment c = new Comment();
                c.setContent(editText.getText().toString());
                //String content=editText.getText().toString();
                SharedPreferences sh =getSharedPreferences("userinfo",
                        Activity.MODE_PRIVATE);
                c.setPublisher_id(sh.getString("user_id", "手机用户18957137642"));
                c.setTo_id(activityid);

                String json = GsonUtil.Java2Json(c);
                RequestParams params = new RequestParams();
                params.add("to_id",json);
                handler2=new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                    }
                };
                HttpFactory.getComClient(handler2).addComment(params);
                commentsList.add(c);
                myrecycleAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comment_activity, menu);
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

    public class MyrecycleAdapter extends  RecyclerView.Adapter<MyrecycleAdapter.CustomViewHolder>{
        private List<Comment> CommentList;
        private Context mContext;

        public MyrecycleAdapter(Context context,List<Comment> comments)
        {
            mContext=context;
            CommentList=comments;
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,null);
            CustomViewHolder viewHolder = new CustomViewHolder(view);
            System.out.print("我来过 onCreateViewHolder");
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position) {
            System.out.println("Result:" + "onBindView");
            final Comment comment = CommentList.get(position);
            final CustomViewHolder holder1=holder;
            handler2=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if(msg.what == Global.SUCCESS){
                        String result = msg.getData().getString("result");
                        User user=(User)Json2Java(result,User.class);
                        //holder.submittext.setText(user.getNick_name());
                        Picasso.with(mContext).load(user.getImage())
                        .error(R.drawable.mengmeizi)
                                .placeholder(R.drawable.hahahaaha)
                                .into(holder1.imageView);
                        holder1.textView.setText((user.getNick_name()));
                    }else{
                        System.out.println("获取信息失败");
                    }
                }
            };
            RequestParams params = new RequestParams();
            params.add("user_id", comment.getPublisher_id());
            HttpFactory.getUserClient(handler2).SearchUser(params);
            //download image using picasso library


         //   holder.textView.setText(Html.fromHtml(comment.getComment_id()));
            holder.context.setText(comment.getContent());

            //handle click event on both title and image click
            holder.textView.setTag(holder);
            holder.imageView.setTag(holder);



            View.OnClickListener clickListener = new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    CustomViewHolder holder = (CustomViewHolder) v.getTag();
                    int position = holder.getAdapterPosition();
                }
            };

            holder.imageView.setOnClickListener(clickListener);
            holder.textView.setOnClickListener(clickListener);

        }

        @Override
        public int getItemCount() {
            return (null != CommentList ? CommentList.size() : 0);
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            protected ImageView imageView;
            protected TextView textView;
            protected TextView context;

            public CustomViewHolder(View view) {
                super(view);
                this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
                this.textView = (TextView) view.findViewById(R.id.title);
                this.context = (TextView) view.findViewById(R.id.place);
            }
        }
    }
    public void nettask() {
        Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == Global.SUCCESS) {
                    String result = msg.getData().getString("result");
                    System.out.println("Result:" + result);
                    commentsList=JsonArray2JavaList(result, Comment.class);
                    //填充内容


                    myrecycleAdapter = new MyrecycleAdapter(Comment_activity.this,commentsList);
                    recyclerView.setAdapter(myrecycleAdapter);
                }
            }
        };

        Intent i = getIntent();
        Bundle b =i.getExtras();
        activityid= b.getString("activity_id");
        HttpFactory.getComClient(mHandler).getComment(activityid);
    }

}
