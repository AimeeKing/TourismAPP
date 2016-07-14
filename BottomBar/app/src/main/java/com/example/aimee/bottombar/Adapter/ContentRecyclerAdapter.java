package com.example.aimee.bottombar.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.aimee.bottombar.R;
import com.example.aimee.bottombar.newServer.model.CommentModel;
import com.example.aimee.bottombar.newServer.model.MActivityModel;
import com.example.aimee.bottombar.newServer.request.UserRequest;
import com.example.aimee.bottombar.newServer.response.CompanyResponse;
import com.example.aimee.bottombar.newServer.response.UserResponse;
import com.example.aimee.bottombar.newServer.utils.Singleton;
import com.example.aimee.bottombar.newServer.utils.statics.Global;
import com.example.aimee.bottombar.newServer.utils.statics.NewHttpFactory;

import java.util.ArrayList;

/**
 * Created by Aimee on 2016/5/2.
 */
//大概改好了
public class ContentRecyclerAdapter extends RecyclerView.Adapter<ContentRecyclerAdapter.CustomerView> {
    private ArrayList<Object> itemArrayList;//放的次序，第0个是推荐理由，第1个是内容，第3个是店家信息，第5个是评论
    private Activity activity;
    private Context context;
    private Handler handler2;
    public ContentRecyclerAdapter(Activity activity, Context context, ArrayList<Object> itemArrayList) {
        this.activity = activity;
        this.context = context;
        this.itemArrayList = itemArrayList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position){
            case 0:
            case 1:
            case 3:
                return position;
            case 2:
            case 4:
                return 2;
            default:return 5;

        }
    }

    @Override
    public CustomerView onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:return new HeadViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_recommend,parent,false));
            case 1:return new ContentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_web,parent,false));
            case 3:return new StoreViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_shop_content,parent,false));
            case 5:return new CommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_comment,parent,false));
            case 2:return new TitleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_title,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(CustomerView holder, int position) {
        switch (position){
            case 0:
                HeadViewHolder headViewholder = (HeadViewHolder)holder;
                headViewholder.recommendTv.setText((String) itemArrayList.get(position));
                break;
            case 1:
                ContentViewHolder contentViewholder = (ContentViewHolder)holder;
                contentViewholder.contentWebView.getSettings().setJavaScriptEnabled(true);
                contentViewholder.contentWebView.loadUrl(((MActivityModel)itemArrayList.get(position)).getContent());
                contentViewholder.contentWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        // TODO Auto-generated method stub
                        view.loadUrl(url);// 使用当前WebView处理跳转
                        return true;//true表示此事件在此处被处理，不需要再广播
                    }

                    @Override   //转向错误时的处理
                    public void onReceivedError(WebView view, int errorCode,
                                                String description, String failingUrl) {
                        // TODO Auto-generated method stub
                        Toast.makeText(context, "Oh no! " + description, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 2:
            case 4:
                TitleViewHolder titleView = (TitleViewHolder) holder;
                titleView.titleTv.setText(position == 2?"店铺 store":"评论 comments");
                break;
            case 3:
                StoreViewHolder storeViewHolder = (StoreViewHolder) holder;
                storeViewHolder.shopNameTV.setText(((CompanyResponse)itemArrayList.get(position)).getCompanyName());
                storeViewHolder.addrTV.setText(((CompanyResponse)itemArrayList.get(position)).getContent());
               break;
            default:
                final CommentViewHolder commentViewHolder = (CommentViewHolder) holder;
                if(itemArrayList.get(position).equals(""))
                    break;
                final CommentModel comment = (CommentModel)itemArrayList.get(position);
                commentViewHolder.place.setText(comment.getContent());
                Global.mHandler=new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        if(msg.what == Global.SUCCESS){
                            String result = msg.getData().getString("result");
                            UserResponse user= JSON.parseObject(result, UserResponse.class);
                            //holder.submittext.setText(user.getNick_name());
//                            Picasso.with(context).load(user.getLogoImgUrl())
//                                    .error(R.drawable.hahahaaha)
//                                    .placeholder(R.drawable.hahahaaha)
//                                    .into(commentViewHolder.thumbnail);
                            ImageRequest imageRequest =new ImageRequest(user.getLogoImgUrl(), new Response.Listener<Bitmap>() {
                                @Override
                                public void onResponse(Bitmap response) {
                                    commentViewHolder.thumbnail.setImageBitmap(response);
                                }
                            }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    commentViewHolder.thumbnail.setImageResource(R.drawable.mengmeizi);
                                }
                            });
                            Singleton.getmInstance(context.getApplicationContext()).addToRequestQueue(imageRequest);
                            commentViewHolder.title.setText((user.getNickName()));
                        }else{
                            System.out.println("获取信息失败");
                        }
                    }
                };
                UserRequest userRequest = new UserRequest();
                userRequest.setUserCode(comment.getPublisherCode());
                NewHttpFactory.getUserClient().search(userRequest);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class CustomerView extends RecyclerView.ViewHolder {
        public CustomerView(View itemView) {
            super(itemView);
        }
    }

    public class HeadViewHolder extends CustomerView{
        private TextView recommendTv;
        public HeadViewHolder(View itemView) {
            super(itemView);
            recommendTv = (TextView) itemView.findViewById(R.id.recommend_tv);
        }
    }

    public class ContentViewHolder extends CustomerView{
        WebView contentWebView;
        public ContentViewHolder(View itemView) {
            super(itemView);
            contentWebView = (WebView) itemView.findViewById(R.id.conent_web);
        }
    }

    private class TitleViewHolder extends CustomerView
    {
        TextView titleTv;
        public TitleViewHolder(View itemView) {
            super(itemView);
            titleTv = (TextView) itemView.findViewById(R.id.title_tv);
        }
    }


    private class CommentViewHolder extends CustomerView {
        TextView title;
        TextView place;
        ImageView thumbnail;
        public CommentViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.shop_name);
            place = (TextView) itemView.findViewById(R.id.place);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        }
    }

    private class StoreViewHolder extends CustomerView{
        TextView shopNameTV;
        TextView addrTV;
        TextView telTV;
        public StoreViewHolder(View itemView) {
            super(itemView);
            shopNameTV = (TextView) itemView.findViewById(R.id.shop_name);
            addrTV = (TextView) itemView.findViewById(R.id.addr_tv);
            telTV = (TextView) itemView.findViewById(R.id.tel_tv);
        }
    }
}
