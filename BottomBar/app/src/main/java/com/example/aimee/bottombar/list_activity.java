package com.example.aimee.bottombar;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.aimee.bottombar.UI.ListDropDownAdapter;
import com.example.aimee.bottombar.tony.po.Activities;
import com.example.aimee.bottombar.tony.utils.statics.Factories.HttpFactory;
import com.example.aimee.bottombar.tony.utils.statics.Global;
import com.squareup.picasso.Picasso;
import com.umeng.message.PushAgent;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.aimee.bottombar.tony.utils.statics.GsonUtil.JsonArray2JavaList;
//这一页是搜索结果页


public class list_activity extends Activity {
    private String headers[]={"排序方式","场地","年龄"};

    private String sort[]={"价格排序","最新排序","综合排序","热度排序"};;

    private String ages[]={"不限","小学","幼儿","宝宝"};

    private String place[]={"不限","户外","室内"};


    private DropDownMenu dropDownMenu;
    private ListDropDownAdapter sortAdapter;
    private List<View> popupViews = new ArrayList<>();

    private List<Activities> activitiesList;
    private ProgressBar progressBar;
    private RecyclerView contentView;
    private MyrecyclerAdapter adapter;

    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_float);

        initView();
        //友盟推送要求，每个activity都要用这个函数，不然会导致广播发送不成功
        PushAgent.getInstance(getBaseContext()).onAppStart();


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

      //  final String url = "http://javatechig.com/?json=get_recent_posts&count=45";
     //   new AsyncHttpTask().execute(url);

        network(bundle.getString("key"));


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






    public class MyrecyclerAdapter extends  RecyclerView.Adapter<MyrecyclerAdapter.CustomViewHolder>{
        private List<Activities> activitiesList;
        private Context mContext;


        public MyrecyclerAdapter(Context context,List<Activities> activities)
        {
            mContext=context;
            activitiesList=activities;

        }

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // each data item is just a string in this case
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,null);
            CustomViewHolder viewHolder = new CustomViewHolder(view);
            return viewHolder;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final CustomViewHolder holder, int position) {
            final Activities activitity = activitiesList.get(position);

            Picasso.with(mContext).load(activitity.getImage())
                    .error(R.drawable.beautyto)
                    .placeholder(R.drawable.beautyto)
                    .into(holder.imageView);



            holder.textView.setText(Html.fromHtml(activitity.getTitle()));


            holder.place.setText(activitity.getDestination());

            //handle click event on both title and image click
            holder.textView.setTag(holder);
            holder.imageView.setTag(holder);

            View.OnClickListener clickListener = new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    CustomViewHolder holder = (CustomViewHolder) v.getTag();
                    int position = holder.getAdapterPosition();
                    Activities activities = activitiesList.get(position);
                    //  Toast.makeText(mContext,feedItem1.getTitle(),Toast.LENGTH_LONG).show();
                    // int id = feedItem1.getId();
                    //然后把id给新的activity,新的activity利用id去查文章
                    int id = 1010;
                   /* Intent i =new Intent(MainActivity.this, Main3Activity.class);

                    //  View sharedView = holder.imageView;//feedItem1.getThumbnail()
                    // String transitionName ="tran_img";
                    //  ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,sharedView,transitionName);
                    //   Bundle bundle=transitionActivityOptions.toBundle();
                    Bundle bundle=new Bundle();
                    bundle.putInt("id",id);
                    startActivity(i, bundle);
                    */
                }
            };

            holder.imageView.setOnClickListener(clickListener);
            holder.textView.setOnClickListener(clickListener);

        }

        @Override
        public int getItemCount() {
            return (null != activitiesList ? activitiesList.size() : 0);
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            protected ImageView imageView;
            protected TextView textView;
            protected  TextView place;

            public CustomViewHolder(View view) {
                super(view);
                this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
                this.textView = (TextView) view.findViewById(R.id.title);
                this.place = (TextView) view.findViewById(R.id.place);

            }
        }
    }



     private void network(String activity_name)
      {
          handler = new Handler(){
              @Override
              public void handleMessage(Message msg) {
                  super.handleMessage(msg);
                  progressBar.setVisibility(View.GONE);
                  if(msg.what== Global.SUCCESS)
                  {
                      String result = msg.getData().getString("result");
                      System.out.println("Result:" + result);
                      activitiesList=JsonArray2JavaList(result, Activities.class);
                      //填充内容

                      adapter = new MyrecyclerAdapter(list_activity.this,activitiesList);
                      contentView.setAdapter(adapter);
                  }
                  else
                  {
                      System.out.println("查找活动失败");
                  }

              }
          };


          String name="activity_name";
          String value = activity_name;
          HttpFactory.getActClient(handler).searchAct(name,value);

      }

}
