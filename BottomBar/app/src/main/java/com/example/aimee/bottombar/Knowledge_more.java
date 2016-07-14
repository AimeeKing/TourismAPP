package com.example.aimee.bottombar;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.text.Html;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aimee.bottombar.Service.MusicService;
import com.example.aimee.bottombar.newServer.model.KnowledgeModel;
import com.example.aimee.bottombar.newServer.model.MActivityModel;
import com.example.aimee.bottombar.newServer.model.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

//暫時改好了，但總覺得缺了設麼，怎麼變成繁體字了
public class Knowledge_more extends AppCompatActivity implements View.OnClickListener{
    private MusicService musicService;
    private Intent playIntent;
    private boolean musicBound = false;
    private ServiceConnection serviceConnection;
    private ArrayList<Song> songList;
    private AppCompatSeekBar seekBar;
    private TextView fromTimeView;
    private TextView endTimeView;
    private TextView title;
    private ImageView backward;
    private ImageView forward;
    private ImageView pause;
    private ImageView play;
    private RelativeLayout play_layout;
    private RelativeLayout cat_layout;
    private ViewPager cardViewPager;
    private Handler timeHandler;
    private PagerAdapter cardPagerAdapter;
    private List<View> viewList;//view数组
    private List<String> textList;
    private double startTime = 0;
    private double endTime = 0;
    private static int forwardtime = 5000;
    private static int backwardtime = 5000;
    private Boolean is_play = false;
    private KnowledgeModel mknowledgeModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_more);
        initview();
        timeHandler = new Handler();
        songList = new ArrayList<>();

        songList.add(new Song("http://mp3hot.9ku.com/hot/2009/10-13/197196.mp3"));
        songList.add(new Song("http://mp3.9ku.com/mp3/187/186352.mp3"));
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MusicService.MusicBinder mbinder = (MusicService.MusicBinder) service;
                musicService = mbinder.getService();
                musicService.setSongs(songList);
                musicBound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                musicBound = false;
            }
        };



    }

    private void initview() {
        Intent knowledgeFromSwipe = getIntent();
        mknowledgeModel = (KnowledgeModel) knowledgeFromSwipe.getExtras().getSerializable("knowledge");
        textList=getTextList(mknowledgeModel);
        seekBar = (AppCompatSeekBar) findViewById(R.id.seekbar);
        fromTimeView = (TextView) findViewById(R.id.fromtime);
        endTimeView = (TextView) findViewById(R.id.endtime);
        title = (TextView) findViewById(R.id.title);
        backward = (ImageView) findViewById(R.id.backward);
        forward = (ImageView) findViewById(R.id.forward);
        pause = (ImageView) findViewById(R.id.pause);
        play = (ImageView) findViewById(R.id.catshow);
        play_layout = (RelativeLayout) findViewById(R.id.play_layout);
        play_layout.setVisibility(View.GONE);
        cat_layout = (RelativeLayout) findViewById(R.id.cat_layout);
        cat_layout.setVisibility(View.VISIBLE);
        cardViewPager = (ViewPager) findViewById(R.id.viewpager);
        forward.setOnClickListener(this);
        backward.setOnClickListener(this);
        pause.setOnClickListener(this);
        play.setOnClickListener(this);
        viewList = setViewList();
        cardPagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position));
                return viewList.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(viewList.get(position));
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };
        cardViewPager.setAdapter(cardPagerAdapter);

    }

    private List<String> getTextList(KnowledgeModel mknowledgeModel) {
        String allText = mknowledgeModel.getContent();
        List<String> textList =new ArrayList<>();
        for(int i=0;i<allText.length();i+=230)
        {
            if(i+460<allText.length())
                textList.add(allText.substring(i,i+230));
            else
                textList.add(allText.substring(i,allText.length()-1));
        }
        return textList;
    }

    private List<View> setViewList() {
        List<View> List = new ArrayList<>();
        for(int i=0;i<textList.size();i++)
        {
            View v = View.inflate(this,R.layout.view_pager,null);
            TextView text = (TextView) v.findViewById(R.id.context);
            text.setText(Html.fromHtml(textList.get(i)));
            if(i == textList.size()-1) {
                v.findViewById(R.id.click).setVisibility(View.VISIBLE);
                v.findViewById(R.id.click).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent toActivityIntent = new Intent(Knowledge_more.this,ContentActivity.class);
                        Bundle bundle = new Bundle();
                        MActivityModel mActivityModel = new MActivityModel();
                        mActivityModel.setContent("http://aimeeking.xicp.net:33727/herb.html");
                        mActivityModel.setImage("http://pic30.nipic.com/20130613/9502787_175918221000_2.jpg");
                        mActivityModel.setTitle("认识常见中草药");
                        mActivityModel.setActivityName("认识常见中草药");
                        mActivityModel.setCode("20160505221144000011");
                        mActivityModel.setShortDesc("中药和西药的概念，二者均具有特定内涵，可说是两类药物本质各自的高度概括。中药和西药，" +
                                "是中医药学和西医药学理论体系内的概念。因此，在确定中药和西药二者的内涵，包括划分原则和标准时，均不能离开各自" +
                                "的医药学理论体系。认识中医药与西医药的理论体系在概念上的相异之处，对于在指导如何合理用药、安全用药等方面均具有重要的意义.");
                        bundle.putSerializable("activity",mActivityModel);
                        toActivityIntent.putExtras(bundle);
                        startActivity(toActivityIntent);
                    }
                });
            }
            else
                v.findViewById(R.id.click).setVisibility(View.INVISIBLE);
            List.add(v);
        }
        return List;
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (playIntent == null) {
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, serviceConnection, BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }

    @Override
    protected void onDestroy() {
        if(musicService != null) {
            stopService(playIntent);
            musicService = null;
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        int temp;
        switch (v.getId()){
            case R.id.backward:
                temp = (int) startTime;
                if((temp-backwardtime)>0)
                {
                    startTime = startTime - backwardtime;
                    setProgress((int) startTime);
                    musicService.seekTo((int)startTime);
                }
                break;
            case R.id.forward:
                temp = (int) startTime;
                if((temp+forwardtime)<endTime)
                {
                    startTime = startTime + forwardtime;
                    setProgress((int) startTime);
                    musicService.seekTo((int)startTime);
                }
                break;
            case R.id.pause:
                //两件事情，音乐暂停，小猫上来，图画消失
                musicService.pause();
                setInAnimation();
                break;
            case R.id.catshow:
                if(startTime == 0) {
                    musicService.play();
                }
                else
                {
                    musicService.restart();
                }
                is_play = true;
                timeHandler.postDelayed(seekbarupdate,100);
                setOutAnimation();
                break;
        }
    }

    private Runnable seekbarupdate = new Runnable() {
        @Override
        public void run() {
            if(endTime == 0) {
                endTime = musicService.getDuration();
                seekBar.setMax((int) endTime);
                endTimeView.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes((long)endTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) endTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                        toMinutes((long) endTime))));
            }
            startTime = musicService.getCurrentTime();
            int minute  = (int) TimeUnit.MILLISECONDS.toMinutes((long)startTime);
            int second = (int) (TimeUnit.MILLISECONDS.toSeconds((long)startTime)-TimeUnit.MILLISECONDS.toSeconds((long)minute));
            fromTimeView.setText(String.format("%d:%d", minute,
                    second));
            seekBar.setProgress((int)startTime);
            if(is_play)
                timeHandler.postDelayed(this,100);
        }
    };

    private void setInAnimation()
    {
        final Animator animatehide = animateRevealhide(getBaseContext(),play_layout);
        animatehide.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ObjectAnimator upcat = ObjectAnimator.ofFloat(
                        play,"translationY",60f,0f
                );
                upcat.setDuration(500);
                cat_layout.setVisibility(View.VISIBLE);
                ObjectAnimator fadein = ObjectAnimator.ofFloat(
                        title,"alpha",0f,1f
                );
                fadein.setDuration(500);
                ObjectAnimator mover = ObjectAnimator.ofFloat(
                        title,"translationY",60f,0f);
                mover.setDuration(500);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(mover).with(fadein).after(upcat);
                animatorSet.start();
                animatehide.removeListener(this);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatehide.start();
    }

    private void setOutAnimation()
    {
        final AnimatorSet set = new AnimatorSet();
        ObjectAnimator upcat = ObjectAnimator.ofFloat(
                play,"translationY",0f,60f
        );
        upcat.setDuration(500);
        ObjectAnimator fadeout = ObjectAnimator.ofFloat(
                title,"alpha",1F,0f
        );
        fadeout.setDuration(500);
        ObjectAnimator mover = ObjectAnimator.ofFloat(
                title,"translationY",0f,60f);
        mover.setDuration(500);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(upcat).with(mover).with(fadeout);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            };

            @Override
            public void onAnimationEnd(Animator animation) {
                set.removeListener(this);
                cat_layout.setVisibility(View.INVISIBLE);
                animateRevealShow(getBaseContext(),play_layout);
                musicService.play();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    private void animateRevealShow(Context context, View view) {
        //int cx = (view.getLeft() + view.getRight())/2;
        //int cy = (view.getTop() + view.getBottom())/2;
        int cx = view.getLeft()+play.getWidth()/2;
        int cy = view.getBottom()+play.getHeight()/2;
        int finalRadius = Math.max(view.getWidth(), view.getHeight());

        Animator animateShow = ViewAnimationUtils.createCircularReveal(view,cx,cy,0,finalRadius);

        view.setVisibility(View.VISIBLE);
        animateShow.start();
    }

    private Animator animateRevealhide(Context context, final View view) {
        int cx = view.getLeft()+play.getWidth()/2;
        int cy = view.getBottom()+play.getHeight()/2;
        int finalRadius = Math.max(view.getWidth(), view.getHeight());

        final Animator animatehide = ViewAnimationUtils.createCircularReveal(view,cx,cy,finalRadius,0);
        animatehide.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.INVISIBLE);
                animatehide.removeListener(this);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        return animatehide;
    }


    @Override
    protected void onPause() {
        super.onPause();
        is_play = false;
    }
}