package com.example.aimee.bottombar.Adapter;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.PasswordTransformationMethod;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cocosw.bottomsheet.BottomSheet;
import com.example.aimee.bottombar.BuyInfoActivity;
import com.example.aimee.bottombar.R;
import com.example.aimee.bottombar.newServer.model.MPackageModel;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;

import java.util.List;

/**
 * Created by Aimee on 2016/5/6.
 */
//这个是套餐（票）的adapter 已经好了
public class TicketRecyclerViewAdapter extends  RecyclerView.Adapter<TicketRecyclerViewAdapter.CustomViewHolder> {
        private List<MPackageModel> packagesList;
        private Context mContext;
        private Activity mactivity;
        private SparseBooleanArray expandState = new SparseBooleanArray();
        public TicketRecyclerViewAdapter(Activity activity,Context context,List<MPackageModel> packagesList)
        {
            this.mactivity =activity;
            mContext=context;
            this.packagesList=packagesList;
            for(int i=0;i<packagesList.size();i++) {
                expandState.append(i,false);
            }
        }


        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_tickets,null);
            CustomViewHolder viewHolder = new CustomViewHolder(view);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(final CustomViewHolder holder, final int position) {
            final MPackageModel  packages = packagesList.get(position);
            holder.title.setText(Html.fromHtml(packages.getTitle()));
            holder.price.setText("现价只要：" + packages.getPrice());
            holder.book.setTag(holder);
            holder.book2.setTag(holder);
            holder.moreinfo.setTag(holder);

            final String text = packages.getContent();
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
                    if(position != packagesList.size()-1) {
                        Intent toBuyTicketIntent = new Intent(mactivity,BuyInfoActivity.class);
                        Bundle packageModelBundle = new Bundle();
                        packageModelBundle.putSerializable("package",packagesList.get(position));
                        packageModelBundle.putInt("type",0);
                        toBuyTicketIntent.putExtras(packageModelBundle);
                        mactivity.startActivity(toBuyTicketIntent);
                    }
                    else
                    {
                        new BottomSheet.Builder(mactivity).title("私人组团").sheet(R.menu.menu_organizing).listener(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which)
                                {
                                    case R.id.new_organize:
                                        Intent toBuyTicketIntent = new Intent(mactivity,BuyInfoActivity.class);
                                        Bundle packageModelBundle = new Bundle();
                                        packageModelBundle.putSerializable("package",packagesList.get(position));
                                        packageModelBundle.putInt("type",1);
                                        toBuyTicketIntent.putExtras(packageModelBundle);
                                        mactivity.startActivity(toBuyTicketIntent);
                                        break;
                                    case R.id.join_organize:
                                        EditText  input =new EditText(mContext);
                                        input.setTransformationMethod(PasswordTransformationMethod
                                                .getInstance());
                                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT
                                        );
                                        lp.setMargins(10,2,10,2);
                                        input.setLayoutParams(lp);
                                        AlertDialog.Builder alertdialog = new AlertDialog.Builder(mContext);
                                        alertdialog.setTitle("请输入团队密码");
                                        alertdialog.setView(input);
                                        alertdialog.setIcon(R.drawable.comment_1);
                                        alertdialog.setPositiveButton("发布", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //input.getText().toString()

                                            }
                                        });
                                        alertdialog.setNegativeButton("取消",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.cancel();
                                                    }
                                                });
                                        alertdialog.show();
                                        break;
                                }
                            }
                        }).show();
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
            protected ExpandableLinearLayout moreinfo;
            protected RelativeLayout Expandbuttom;
            protected TextView moreInfoTV;
            public CustomViewHolder(View view) {
                super(view);
                this.title = (TextView) view.findViewById(R.id.title);
                this.price = (TextView) view.findViewById(R.id.prise);
                this.PublishTime = (TextView) view.findViewById(R.id.publishtime);
                this.Expandbuttom = (RelativeLayout) view.findViewById(R.id.button);
                this.book = (Button) view.findViewById(R.id.buy);
                this.book2 = (Button) view.findViewById(R.id.buyit);
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
