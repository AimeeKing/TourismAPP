package com.example.aimee.bottombar.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.aimee.bottombar.ContentActivity;
import com.example.aimee.bottombar.R;
import com.example.aimee.bottombar.newServer.model.MActivityModel;
import com.example.aimee.bottombar.newServer.utils.Singleton;

import java.util.List;

/**
 * Created by Aimee on 2016/5/7.
 */
public class ActivityItemRecyclerViewAdapter extends RecyclerView.Adapter<ActivityItemRecyclerViewAdapter.CustomViewHolder> {
    private List<MActivityModel> activitiesList;
    private Context mContext;
    private Activity mActivity;
    private int mtype;
    private static final int BIGTYPE = 0;
    private static final int SMALLTYPE = 1;

    public ActivityItemRecyclerViewAdapter(List<MActivityModel> activitiesList, Activity mActivity, Context mContext, int mtype) {
        this.activitiesList = activitiesList;
        this.mActivity = mActivity;
        this.mContext = mContext;
        this.mtype = mtype;
    }

    public ActivityItemRecyclerViewAdapter(List<MActivityModel> activitiesList, Activity mActivity, Context mContext) {
        this.activitiesList = activitiesList;
        this.mActivity = mActivity;
        this.mContext = mContext;
        this.mtype = 0;
    }

    @Override
    public int getItemViewType(int position) {
        return mtype;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == BIGTYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_big, null);
            BigCardViewHolder viewHolder = new BigCardViewHolder(view);
            return viewHolder;
        }
        else if(viewType == SMALLTYPE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,null);
            SmallCardViewHolder viewHolder = new SmallCardViewHolder(view);
            return viewHolder;
        }
        return null;
    }


    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {
        final MActivityModel activity = activitiesList.get(position);
        if(mtype == BIGTYPE) {
            final BigCardViewHolder bigCardViewHolder = (BigCardViewHolder) holder;
            ImageRequest imageRequest = new ImageRequest(activity.getImage(), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    bigCardViewHolder.imageView.setImageBitmap(response);
                }
            }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    bigCardViewHolder.imageView.setImageResource(R.drawable.mengmeizi);
                }
            });

            Singleton.getmInstance(mContext.getApplicationContext()).addToRequestQueue(imageRequest);

            bigCardViewHolder.textView.setText(Html.fromHtml(activity.getTitle()));
            bigCardViewHolder.context.setText("地点位于：" + activity.getDestination());

            //handle click event on both title and image click
            bigCardViewHolder.textView.setTag(holder);
            bigCardViewHolder.imageView.setTag(holder);


            View.OnClickListener clickListener = new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    CustomViewHolder holder = (CustomViewHolder) v.getTag();
                    int position = holder.getAdapterPosition();
                    MActivityModel activity = activitiesList.get(position);
                    //然后把id给新的activity,新的activity利用id去查文章
                    Intent i = new Intent(mActivity, ContentActivity.class);
                    Bundle bundle2 = new Bundle();
                    bundle2.putSerializable("activity", activity);
                    i.putExtras(bundle2);
                    mActivity.startActivity(i);
                }
            };

            bigCardViewHolder.imageView.setOnClickListener(clickListener);
            bigCardViewHolder.textView.setOnClickListener(clickListener);
        }
        else if(mtype == SMALLTYPE)
        {
            final SmallCardViewHolder smallCardViewHolder = (SmallCardViewHolder) holder;
            ImageRequest imageRequest =new ImageRequest(activity.getImage(), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    smallCardViewHolder.imageView.setImageBitmap(response);
                }
            }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    smallCardViewHolder.imageView.setImageResource(R.drawable.hahahaaha);
                }
            });

            Singleton.getmInstance(mContext.getApplicationContext()).addToRequestQueue(imageRequest);

            smallCardViewHolder.textView.setText(Html.fromHtml(activity.getTitle()));


            smallCardViewHolder.place.setText(activity.getDestination());

            //handle click event on both title and image click
            smallCardViewHolder.textView.setTag(holder);
            smallCardViewHolder.imageView.setTag(holder);

            View.OnClickListener clickListener = new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    CustomViewHolder holder = (CustomViewHolder) v.getTag();
                    int position = holder.getAdapterPosition();
                    MActivityModel activity = activitiesList.get(position);
                    //然后把id给新的activity,新的activity利用id去查文章
                    Intent i = new Intent(mActivity, ContentActivity.class);
                    Bundle bundle2 = new Bundle();
                    bundle2.putSerializable("activity", activity);
                    i.putExtras(bundle2);
                    mActivity.startActivity(i);
                }
            };

            smallCardViewHolder.imageView.setOnClickListener(clickListener);
            smallCardViewHolder.textView.setOnClickListener(clickListener);
        }
    }

    @Override
    public int getItemCount() {
        return (null != activitiesList ? activitiesList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public CustomViewHolder(View view) {
            super(view);
        }
    }


    public class BigCardViewHolder extends CustomViewHolder {
        protected ImageView imageView;
        protected TextView textView;
        protected TextView context;

        public BigCardViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.textView = (TextView) view.findViewById(R.id.title);
            this.context = (TextView) view.findViewById(R.id.context);
        }
    }

    public class SmallCardViewHolder extends CustomViewHolder {
        protected ImageView imageView;
        protected TextView textView;
        protected  TextView place;

        public SmallCardViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.textView = (TextView) view.findViewById(R.id.title);
            this.place = (TextView) view.findViewById(R.id.place);

        }
    }


}
