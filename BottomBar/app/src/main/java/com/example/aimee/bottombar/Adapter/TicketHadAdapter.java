package com.example.aimee.bottombar.Adapter;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aimee.bottombar.R;
import com.example.aimee.bottombar.newServer.model.PurchaseModel;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;

import java.util.List;

/**
 * Created by Aimee on 2016/6/3.
 */
public class TicketHadAdapter extends  RecyclerView.Adapter<TicketHadAdapter.CustomViewHolder>  {
    private Context mContext;
    private Activity mactivity;
    private List<PurchaseModel> PurchaseModelList;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    public TicketHadAdapter(List<PurchaseModel> purchaseModelList, Context mContext, Activity mactivity) {
        PurchaseModelList = purchaseModelList;
        this.mContext = mContext;
        this.mactivity = mactivity;
        for(int i=0;i<PurchaseModelList.size();i++) {
            expandState.append(i,false);
        }
    }

    @Override
    public TicketHadAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_ticket_had,null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TicketHadAdapter.CustomViewHolder holder, final int position) {
        final PurchaseModel purchase = PurchaseModelList.get(position);
        holder.title.setText(Html.fromHtml("订单号："+purchase.getCode()));
        holder.price.setText("共计" + purchase.getCount().toString() +"张");
        holder.book.setTag(holder);
        holder.moreinfo.setTag(holder);

        final String text = purchase.getContent();
        holder.moreInfoTV.setText(text);
        holder.moreinfo.setInterpolator( Utils.createInterpolator(Utils.ACCELERATE_DECELERATE_INTERPOLATOR));
        holder.moreinfo.setExpanded(expandState.get(position));
        holder.moreinfo.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreClose() {
                super.onPreClose();
                createRotateAnimator(holder.Expandbuttom,0f,180f).start();
                expandState.put(position,true);
            }

            @Override
            public void onPreOpen() {
                super.onPreOpen();
                createRotateAnimator(holder.Expandbuttom,180f,0f).start();
                expandState.put(position,false);
            }
        });
//            holder.moreinfo.setOnExpandStateChangeListener(new ExpandableTextView.OnExpandStateChangeListener() {
//                @Override
//                public void onExpandStateChanged(TextView textView, boolean b) {
//                    if(b)
//                    {
//
//                        textView.setText(text);
//                    }
//                    else
//                        textView.setText("详细信息\n\n\n\n\n\n\n");
//                }
//            });
        holder.Expandbuttom.setRotation(expandState.get(position)?180f:0f);
        holder.Expandbuttom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.moreinfo.toggle();
            }
        });
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        };
        holder.book.setOnClickListener(clickListener);
        holder.book.setText(purchase.getStatus());
    }

    @Override
    public int getItemCount() {
        return (null != PurchaseModelList ? PurchaseModelList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView title;
        protected TextView price;
        protected TextView PublishTime;
        protected Button book;
        protected ExpandableLinearLayout moreinfo;
        protected RelativeLayout Expandbuttom;
        protected TextView moreInfoTV;
        public CustomViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.title);
            this.price = (TextView) view.findViewById(R.id.prise);
            this.PublishTime = (TextView) view.findViewById(R.id.publishtime);
            this.Expandbuttom = (RelativeLayout) view.findViewById(R.id.button);
            this.book = (Button) view.findViewById(R.id.use_it);
            this.moreinfo = (ExpandableLinearLayout) view.findViewById(R.id.expand_text_view);
            this.moreInfoTV = (TextView) view.findViewById(R.id.expandable_text);
        }
    }
    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }
}
