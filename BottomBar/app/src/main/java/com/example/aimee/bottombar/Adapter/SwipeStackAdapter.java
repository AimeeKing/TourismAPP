package com.example.aimee.bottombar.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aimee.bottombar.Knowledge_more;
import com.example.aimee.bottombar.R;
import com.example.aimee.bottombar.newServer.model.KnowledgeModel;

import java.util.List;

/**
 * Created by Aimee on 2016/4/23.
 */
//ok
public class SwipeStackAdapter extends BaseAdapter {
    private List<KnowledgeModel> listItems;
    private Context mcontext;
    private Activity mactivity;
    public SwipeStackAdapter(List<KnowledgeModel> listItems, Context mcontext, Activity mactivity) {
        this.listItems = listItems;
        this.mcontext = mcontext;
        this.mactivity = mactivity;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = mactivity.getLayoutInflater().inflate(R.layout.knowledgecard,parent,false);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        //先不用这个提交代码的时候用
        final ImageView img = (ImageView) convertView.findViewById(R.id.img);
        title.setText(listItems.get(position).getNlgName());
////        img.setImageResource(listItems.get(position).getImage());
//        ImageRequest imageRequest =new ImageRequest(listItems.get(position).getImage(), new Response.Listener<Bitmap>() {
//            @Override
//            public void onResponse(Bitmap response) {
//                img.setImageBitmap(response);
//            }
//        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                img.setImageResource(R.drawable.mengmeizi);
//            }
//        });
//
//        Singleton.getmInstance(mcontext.getApplicationContext()).addToRequestQueue(imageRequest);
        img.setImageResource(listItems.get(position).getImageResourse());
        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(mcontext,listItems.get(position).getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(mactivity,Knowledge_more.class);
                Bundle knowledgeBundler = new Bundle();
                knowledgeBundler.putSerializable("knowledge",listItems.get(position));
                intent.putExtras(knowledgeBundler);
                mactivity.startActivity(intent);
            }
        };
        //img.setOnClickListener(onClickListener);
        title.setOnClickListener(onClickListener);
            return  convertView;
    }
}
