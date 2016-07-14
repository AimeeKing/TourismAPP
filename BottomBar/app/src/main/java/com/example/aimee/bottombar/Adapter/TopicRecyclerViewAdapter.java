package com.example.aimee.bottombar.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cocosw.bottomsheet.BottomSheet;
import com.cocosw.bottomsheet.BottomSheetHelper;
import com.example.aimee.bottombar.R;
import com.example.aimee.bottombar.newServer.model.TopicModel;
import com.example.aimee.bottombar.newServer.request.UserRequest;
import com.example.aimee.bottombar.newServer.request.ZanRequest;
import com.example.aimee.bottombar.newServer.response.UserResponse;
import com.example.aimee.bottombar.newServer.response.ZanResponse;
import com.example.aimee.bottombar.newServer.utils.statics.Global;
import com.example.aimee.bottombar.newServer.utils.statics.NewHttpFactory;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Aimee on 2016/5/5.
 */
public class TopicRecyclerViewAdapter extends  RecyclerView.Adapter<TopicRecyclerViewAdapter.CustomViewHolder> {
        private Handler getUserNameHandler;
        private Activity mactivity;
        private List<TopicModel> TopicsList;
        private Context mContext;
        private Map<String,Integer> mzanList = new HashMap<>();
        private Map<String,UserResponse> userList = new HashMap<>();
        private Handler maddZanHandler;
        public TopicRecyclerViewAdapter(Activity activity, Context context, List<TopicModel> topicsItems,Handler addZanHandler)//, List<Zan> zanList
        {
//            mzanList = zanList;
            mactivity = activity;
            mContext=context;
            TopicsList=topicsItems;
            maddZanHandler = addZanHandler;
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_topic,null);
            CustomViewHolder viewHolder = new CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final CustomViewHolder holder, int position) {
            final TopicModel topic = TopicsList.get(position);


            Picasso.with(mContext).load(topic.getImage())
                    .error(R.drawable.mengmeizi)
                    .placeholder(R.drawable.mengmeizi)
                    .into(holder.imageView);


            holder.textView.setText(Html.fromHtml(topic.getTitle()));

            holder.textView.setTag(holder);
            holder.imageView.setTag(holder);
            holder.ev.setText(topic.getContent());
            holder.share.setTag(holder);
            holder.good.setTag(holder);

//            getUserNameHandler =new Handler(){
//                @Override
//                public void handleMessage(Message msg) {
//                    super.handleMessage(msg);
//                    if(msg.what == Global.SUCCESS){
//                        String result = msg.getData().getString("result");
//                        User user=(User)Json2Java(result,User.class);
//                        holder.submittext.setText(user.getNick_name());
//                    }else{
//                        System.out.println("获取信息失败");
//                    }
//                }
//            };
//            RequestParams params = new RequestParams();
//            params.add("user_id",topic.getPublisherCode());
//            HttpFactory.getUserClient(getUserNameHandler).SearchUser(params);
//
            getUserNameNetTask(topic.getPublisherCode(),holder.submittext);
            getTopicZanCountNetTask(topic.getCode(),holder.good);
//            holder.good.setText("点赞（" + 0 + "）");// zan.get(position)


            View.OnClickListener clickListener = new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    CustomViewHolder holder = (CustomViewHolder) v.getTag();
                    int position = holder.getAdapterPosition();
                    TopicModel Topicitem1 = TopicsList.get(position);
                    Toast.makeText(mContext, Topicitem1.getTitle(), Toast.LENGTH_SHORT).show();

                }
            };

            holder.imageView.setOnClickListener(clickListener);
            holder.textView.setOnClickListener(clickListener);
            holder.good.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final CustomViewHolder holder = (CustomViewHolder) v.getTag();
                    addTopicZanCountNetTask(topic.getCode());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            maddZanHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mzanList.put( topic.getCode(),mzanList.get(topic.getCode())+1);
                                    holder.good.setText("点赞（" + mzanList.get(topic.getCode()) + "）");
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

    private void getUserNameNetTask(final String code, final TextView submittext) {
        Global.mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == Global.SUCCESS) {
                    String result = msg.getData().getString("result");
                    UserResponse userResponse = JSON.parseObject(result,UserResponse.class);
                    System.out.println("Result:"+JSON.toJSONString(userResponse));
                    userList.put(code,userResponse);
                    submittext.setText(userResponse.getNickName());
                }
            }
        };
        //新建request 然后在request里面添加参数
        UserRequest request = new UserRequest();
        request.setUserCode(code);
        //工厂类获取Client对象 进行查询
        NewHttpFactory.getUserClient().search(request);
    }

    private BottomSheet.Builder getShareActions(String text) {
            final Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, text);

            return BottomSheetHelper.shareAction(mactivity, shareIntent);
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

    void getTopicZanCountNetTask(final String code, final TextView v)
    {
        Global.mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == Global.SUCCESS) {
                   String result = msg.getData().getString("result");
                    ZanResponse zanResponse = JSON.parseObject(result,ZanResponse.class);
                    System.out.println("Result:"+JSON.toJSONString(zanResponse));
                    mzanList.put(code,zanResponse.getCount());
                  v.setText("点赞（" + zanResponse.getCount() + "）");
                }
            }
        };
        //新建request 然后在request里面添加参数
        ZanRequest request = new ZanRequest();
        request.setObjTypeFlag(1);
        request.setRefObjectCode(code);
        //工厂类获取Client对象 进行查询
        NewHttpFactory.getZanClient().getCount(request);
    }
    void addTopicZanCountNetTask(final String code)
    {
        Global.mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == Global.SUCCESS) {
                    String result = msg.getData().getString("result");
                    ZanResponse zanResponse = JSON.parseObject(result,ZanResponse.class);
                    System.out.println("Result:"+JSON.toJSONString(zanResponse));
                    mzanList.put(code,zanResponse.getCount());
                }
            }
        };
        //新建request 然后在request里面添加参数
        ZanRequest request = new ZanRequest();
        request.setObjTypeFlag(1);
        request.setRefObjectCode(code);
        request.setRefUserCode("20160505221144000006");//要改的
        //工厂类获取Client对象 进行查询
        NewHttpFactory.getZanClient().addZan(request);
    }

}
