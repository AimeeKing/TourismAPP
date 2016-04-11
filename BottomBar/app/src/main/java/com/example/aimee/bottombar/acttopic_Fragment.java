package com.example.aimee.bottombar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;
import com.cocosw.bottomsheet.BottomSheetHelper;
import com.example.aimee.bottombar.tony.po.Topic;
import com.example.aimee.bottombar.tony.po.User;
import com.example.aimee.bottombar.tony.utils.statics.Factories.HttpFactory;
import com.example.aimee.bottombar.tony.utils.statics.Global;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.loopj.android.http.RequestParams;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.aimee.bottombar.tony.utils.statics.GsonUtil.Json2Java;
import static com.example.aimee.bottombar.tony.utils.statics.GsonUtil.JsonArray2JavaList;

//这个是第三个界面的话题界面
public class acttopic_Fragment extends Fragment {
    private RecyclerView recyclerView;
    private MyrecycleAdapter myrecycleAdapter;
    private ProgressBar progressBar;
    private FloatingActionsMenu  mfab;
    private FloatingActionButton afab;
    private FloatingActionButton tfab;
    private List<Topic> topicList;

    private Handler handler1;
    private Handler handler2;

    private ArrayList<Integer> zan=new ArrayList<>();
    private ArrayList<String> writer=new ArrayList<>();

    private final int RESULT_TOPIC=1;
    private final int RESULT_ACTIVITY=2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       //这些真正的要用上传来做
        zan.add(5);
        zan.add(4);
        zan.add(3);
        zan.add(3);
        writer.add("小王");
        writer.add("昨天刚见过");
        writer.add("我宝宝最棒");
        writer.add("那年哪一天");
        writer.add("哎呦不错哦");
        handler1=new Handler();


        View v= inflater.inflate(R.layout.fragment_acttopic, container, false);
        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar.setVisibility(View.VISIBLE);





        mfab= (FloatingActionsMenu) v.findViewById(R.id.multiple_actions);
        afab = (FloatingActionButton) v.findViewById(R.id.add_activity);
        tfab = (FloatingActionButton) v.findViewById(R.id.add_topic);
        afab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),add_actplan.class);
                startActivityForResult(i, RESULT_ACTIVITY);
            }
        });


        tfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),add_Topic_Acitvity.class);
                startActivityForResult(i, RESULT_TOPIC);
            }
        });


        nettask();
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
               Topic topic= (Topic) b.getSerializable("activity");
                topicList.add(topic);
                zan.add(0);
                writer.add(shp.getString("username","手机用户18957137642"));
                myrecycleAdapter.notifyDataSetChanged();
                break;
            case RESULT_TOPIC:
                 b=data.getExtras();
                break;
        }


    }

    public class MyrecycleAdapter extends  RecyclerView.Adapter<MyrecycleAdapter.CustomViewHolder>{
        private List<Topic> TopicsList;
        private Context mContext;

        public MyrecycleAdapter(Context context,List<Topic> topicsItems)
        {
            mContext=context;
            TopicsList=topicsItems;
        }

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // each data item is just a string in this case
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_topic,null);
            CustomViewHolder viewHolder = new CustomViewHolder(view);
            return viewHolder;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final CustomViewHolder holder, int position) {
            final Topic topic = TopicsList.get(position);

            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            //download image using picasso library
            Picasso.with(mContext).load(topic.getImage())
                    .error(R.drawable.mengmeizi)
                    .placeholder(R.drawable.mengmeizi)
                    .into(holder.imageView);


            holder.textView.setText(Html.fromHtml(topic.getTitle()));


            //handle click event on both title and image click
            holder.textView.setTag(holder);
            holder.imageView.setTag(holder);
            holder.ev.setText(topic.getContent());
            holder.share.setTag(holder);
            holder.good.setTag(holder);


            handler2=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if(msg.what == Global.SUCCESS){
                        String result = msg.getData().getString("result");
                        User user=(User)Json2Java(result,User.class);
                        holder.submittext.setText(user.getNick_name());
                    }else{
                        System.out.println("获取信息失败");
                    }
                }
            };
            RequestParams params = new RequestParams();
            params.add("user_id",topic.getPublisher_id());
            HttpFactory.getUserClient(handler2).SearchUser(params);

          //  holder.submittext.setText(writer.get(position));

            holder.good.setText("点赞（" + zan.get(position) + "）");


            View.OnClickListener clickListener = new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    CustomViewHolder holder = (CustomViewHolder) v.getTag();
                    int position = holder.getAdapterPosition();
                    Topic Topicitem1 = topicList.get(position);
                     Toast.makeText(mContext, Topicitem1.getTitle(), Toast.LENGTH_SHORT).show();

                }
            };

            holder.imageView.setOnClickListener(clickListener);
            holder.textView.setOnClickListener(clickListener);
            holder.good.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击了分享
                    final CustomViewHolder holder = (CustomViewHolder) v.getTag();
                    final int position = holder.getAdapterPosition();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            handler1.post(new Runnable() {
                                @Override
                                public void run() {
                                    zan.add(position, zan.get(position) + 1);
                                    holder.good.setText("点赞（" + zan.get(position) + "）");
                                    Toast.makeText(mContext, "好激动，一个赞", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).start();
                }
            });

            holder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BottomSheet sheet;
                    sheet = getShareActions("分享好友").title("分享好友").limit(R.integer.no_limit).build();
                    sheet.show();
                }
            });

        }
        private BottomSheet.Builder getShareActions(String text) {
            final Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, text);

            return BottomSheetHelper.shareAction(getActivity(), shareIntent);
        }
        @Override
        public int getItemCount() {
            return (null != TopicsList ? TopicsList.size() : 0);
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            protected ImageView imageView;
            protected TextView textView;
            protected TextView submittext;
            protected ExpandableTextView ev;
            protected Button share;
            protected Button good;

            public CustomViewHolder(View view) {
                super(view);
                this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
                this.textView = (TextView) view.findViewById(R.id.title);
                this.ev=(ExpandableTextView) view.findViewById(R.id.expand_text_view);
                this.good= (Button) view.findViewById(R.id.button);
                this.share = (Button) view.findViewById(R.id.share);
                this.submittext = (TextView) view.findViewById(R.id.submittitle);

            }
        }
    }


    void nettask()
    {
        Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == Global.SUCCESS) {
                    progressBar.setVisibility(View.GONE);
                    String result = msg.getData().getString("result");
                    System.out.println("Result:" + result);
                    topicList=JsonArray2JavaList(result, Topic.class);
                    //填充内容

                    myrecycleAdapter = new MyrecycleAdapter(getActivity(),topicList);
                    recyclerView.setAdapter(myrecycleAdapter);
                }
            }
        };


        HttpFactory.getTopicClient(mHandler).getTopic();
    }


/*
    private class AsyncHttpTask extends AsyncTask<String,Void,Integer> {


        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            HttpURLConnection urlConnection;
            try {
                URL url=new URL(params[0]);
                urlConnection=(HttpURLConnection)url.openConnection();
                int statusCode=urlConnection.getResponseCode();
                if(statusCode == 200)
                {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuffer response = new StringBuffer();
                    String line;
                    while((line=r.readLine())!=null)
                    {
                        response.append(line);
                    }

                    parseResult(response.toString());
                    result = 1;//表示成功
                }
                else
                    result = 0;//表示获取数据失败

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  result;
        }

        @Override
        protected void onPreExecute() {
            //之前做的
            getActivity().setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected void onPostExecute(Integer result) {
            progressBar.setVisibility(View.GONE);

            if(result == 1)
            {
                myrecycleAdapter = new MyrecycleAdapter(getActivity(),feedsList);
                recyclerView.setAdapter(myrecycleAdapter);
            }
            else
                Toast.makeText(getActivity(), "fail to fetch data", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");
            feedsList = new ArrayList<>();

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                FeedItem item = new FeedItem();
                item.setTitle(post.optString("title"));
                item.setThumbnail(post.optString("thumbnail"));

                feedsList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
*/



}
