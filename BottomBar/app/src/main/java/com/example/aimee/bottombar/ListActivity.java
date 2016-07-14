package com.example.aimee.bottombar;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.alibaba.fastjson.JSON;
import com.example.aimee.bottombar.Adapter.ActivityItemRecyclerViewAdapter;
import com.example.aimee.bottombar.View.ListDropDownAdapter;
import com.example.aimee.bottombar.newServer.model.MActivityModel;
import com.example.aimee.bottombar.newServer.request.MActivityRequest;
import com.example.aimee.bottombar.newServer.response.MActivityResponse;
import com.example.aimee.bottombar.newServer.utils.statics.Global;
import com.example.aimee.bottombar.newServer.utils.statics.NewHttpFactory;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//这一页是搜索结果页
//更改完成

public class ListActivity extends Activity {
    private String headers[]={"排序方式","场地","年龄"};

    private String sort[]={"价格排序","最新排序","综合排序","热度排序"};;

    private String ages[]={"不限","0~3岁","3~6岁","低年级","中年级","高年级"};

    private String place[]={"不限","户外","室内"};


    private DropDownMenu dropDownMenu;
    private ListDropDownAdapter sortAdapter;
    private List<View> popupViews = new ArrayList<>();

    private List<MActivityModel> activitiesList;
    private ProgressBar progressBar;
    private RecyclerView contentView;
    private ActivityItemRecyclerViewAdapter adapter;
    private MActivityRequest mActivityRequest ;
//    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        initView();


    }

    private void initmenu()
    {
        final String menus[][]={sort,place,ages};
        for(int i=0;i<3;i++) {
            final int pos=i;
            ListView sortview = new ListView(this);
            sortview.setDividerHeight(0);
            sortAdapter = new ListDropDownAdapter(this, Arrays.asList(menus[i]));
            sortview.setAdapter(sortAdapter);
            sortview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    sortAdapter.setCheckItem(position);
                    dropDownMenu.setTabText(position == 0 ? headers[pos] : menus[pos][position]);
                    dropDownMenu.closeMenu();
                    System.out.println("下拉菜单"+"现在是"+(position == 0 ? headers[pos] : menus[pos][position]));
                    switch (pos) {
                        case 1:
                            if(position!=0)
                                mActivityRequest.setDestType(position);
                        case 2:
                            if(position!=0)
                                mActivityRequest.setAgeFlag(position);
                    }
                    listActivityNetTask(mActivityRequest);
                    progressBar.setVisibility(View.VISIBLE);
                }

            });
            popupViews.add(sortview);
        }

    }

    private void initView() {
        progressBar= (ProgressBar) findViewById(R.id.progress_bar);
        dropDownMenu= (DropDownMenu) findViewById(R.id.dropDownMenu);
        View v =findViewById(R.id.list_toolbar);
        //设置搜索框方法
        EditText editText= (EditText) v.findViewById(R.id.search_et_input);
        Bundle bundle=getIntent().getExtras();
        editText.setText(bundle.getString("key"));
        editText.clearFocus();//失去焦点

        //设置下拉框
        initmenu();
        //放下面的recycle
        contentView = new RecyclerView(this);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);;
        contentView.setLayoutManager(layoutManager);


        mActivityRequest = new MActivityRequest();
        mActivityRequest.setContext(bundle.getString("key"));
        listActivityNetTask(mActivityRequest);


        dropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
    }

    @Override
    public void onBackPressed() {
        //退出activity前关闭菜单
        if (dropDownMenu.isShowing()) {
            dropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_activity, menu);
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



     private void listActivityNetTask(MActivityRequest mActivityRequest)
      {
          Global.mHandler = new Handler(){
              @Override
              public void handleMessage(Message msg) {
                  super.handleMessage(msg);
                  progressBar.setVisibility(View.GONE);
                  if(msg.what== Global.SUCCESS)
                  {
                      String result = msg.getData().getString("result");
                      System.out.println("Result:" + result);
                      activitiesList= (JSON.parseObject(result, MActivityResponse.class)).getActivityModels();
                      //填充内容
                      adapter = new ActivityItemRecyclerViewAdapter(activitiesList, ListActivity.this, getBaseContext(), 1);
                      contentView.setAdapter(adapter);
                  }
                  else
                  {
                      System.out.println("查找活动失败");
                  }

              }
          };
          
          NewHttpFactory.getActivityClient().searchActivity(mActivityRequest);

      }

}
