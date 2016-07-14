package com.example.aimee.bottombar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.aimee.bottombar.Adapter.SwipeStackAdapter;
import com.example.aimee.bottombar.newServer.model.KnowledgeModel;

import java.util.ArrayList;
import java.util.List;

import link.fls.swipestack.SwipeStack;
//暫時弄好了
public class KnowledgeSwipeStake extends AppCompatActivity implements View.OnClickListener {
    private int[] imglist= new int[]{
            R.drawable.knowledge_card_6,R.drawable.knowledge_card_7,R.drawable.knowledge_card_8,R.drawable.shengnongjia,R.drawable.knowledge_card_9};
    private String[] titlelist = new String[]{"小陨石的旅行",
            "冰激凌的来历","为什么元宵节吃汤圆呢","神农尝百草","小鸭子找妈妈"};
    private SwipeStack swipeStack;
    private SwipeStackAdapter swipeStackadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_swipe_stake);
        swipeStack = (SwipeStack) findViewById(R.id.cardview);
                            swipeStackadapter = new SwipeStackAdapter(setData(imglist,titlelist), getBaseContext(), KnowledgeSwipeStake.this);
                    swipeStack.setAdapter(swipeStackadapter);
//                    swipeStack.setOnClickListener(null);
                    swipeStack.setListener(new SwipeStack.SwipeStackListener() {
                        @Override
                        public void onViewSwipedToLeft(int position) {
                            //Toast.makeText(this,"真棒",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onViewSwipedToRight(int position) {
                            //Toast.makeText(this,((knowledge)swipeStackadapter.getItem(position)).getTitle(),Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onStackEmpty() {
                            Toast.makeText(getBaseContext(),"宝贝，没有啦，明天再来看吧", Toast.LENGTH_SHORT).show();
                        }

                    });

//        getDataNetTask();
    }
//太耗时间，暂时不用这个
//    private void getDataNetTask() {
//        Global.mHandler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                if(msg.what == Global.SUCCESS){
//                    String result = msg.getData().getString("result");
//                    KnowledgeResponse mKnowledgeResponse= JSON.parseObject(result, KnowledgeResponse.class);
//                    List<KnowledgeModel> knowledgeList = mKnowledgeResponse.getNlgList();
//                    swipeStackadapter = new SwipeStackAdapter(knowledgeList, getBaseContext(), KnowledgeSwipeStake.this);
//                    swipeStack.setAdapter(swipeStackadapter);
////                    swipeStack.setOnClickListener(null);
//                    swipeStack.setListener(new SwipeStack.SwipeStackListener() {
//                        @Override
//                        public void onViewSwipedToLeft(int position) {
//                            //Toast.makeText(this,"真棒",Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onViewSwipedToRight(int position) {
//                            //Toast.makeText(this,((knowledge)swipeStackadapter.getItem(position)).getTitle(),Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onStackEmpty() {
//                            Toast.makeText(getBaseContext(),"宝贝，没有啦，明天再来看吧", Toast.LENGTH_SHORT).show();
//                        }
//
//                    });
//                }else{
//                    System.out.println("获取信息失败");
//                }
//            }
//        };
//        KnowledgeRequest knowledgeRequest = new KnowledgeRequest();
//        NewHttpFactory.getKnowledgeClient().listKnowledge(knowledgeRequest);
//    }

    private List<KnowledgeModel> setData(int [] imglist, String [] titlelist) {
        List<KnowledgeModel> listItems = new ArrayList<>();
        KnowledgeModel know;
        for(int i=0;i<imglist.length;i++)
        {
            know = new KnowledgeModel();
            know.setNlgName(titlelist[i]);
            know.setImageResourse(imglist[i]);
            know.setContent("神农教会人们耕田种粮食后，看到人们经常因为乱吃东西而得病，甚至丧命；在疾病面前，人类一点办法都没有，只能等死，神农心里很是焦急，他决心要亲自尝遍所有的植物.这样，就可以知道什么是可以吃的，什么是不能吃的；什么是有害的，什么是能够治病的.下了决心后，神农就做了两只大口袋，一只挂在身子的左边，一只挂在身子的右边.他每尝一样东西，觉得可以吃的，就放在左边的口袋里，将来给人吃；觉得能治病的，就放在右边的口袋里，将来当药用.\n" +
                    "　　神农一出门，就见前面一片矮绿树丛中长着许多可爱的小嫩叶，神农采了一片，刚含进嘴里，就滑到肚子里去了.那片小嫩叶也在神农的肚子里漂来漂去，把他的内脏都擦洗得清清爽爽.神农觉得舒服极了，于是他把它放进左边的口袋里，并给它取名“查”，也就是我们现在用来泡茶的茶叶.\n" +
                    "　　第二天，神农又发现了许多淡红色的小花，它们的形状像一只只飞舞的蝴蝶.神农采了一朵花放进嘴里，只觉得甜津津的，浓香四溢，神农给花取名为“甘草”，把它放进了右边的口袋.就这样，神农每天不停地走啊走，他的足迹遍布了江河山川，高山峻岭.他尝遍了各种花草，也认识了许多药物，用它们救了无数人的性命.\n" +
                    "　　有一次，一个病人得了急病，他需要的药草很难找.神农找了很久，终于发现它长在一座陡峭的岩壁上，这岩壁又高又陡又光滑，根本没有落脚的地方，连猿猴都难以攀登.人们见了，连连摇头，叹息这药草生长的地方实在太高太险，人想上去，比登天还难.神农救人心切，他动手搭起了一个木头过架，顺着这个架子慢慢地攀缘上去，终于爬到了岩顶，采到了草药，救了这个病人.相传神农搭架子采草药的地方，人们称它为神农架.\n" +
                    "　　神农背着满满两口袋的药草，仍在不停地采摘、品尝.有时偶尔尝到毒草，他就赶快拿出第一次采到的“查”，吞下肚去，毒就解掉了.可是有一次，神农不幸尝到了“断肠草”.这种毒草实在太厉害了，神农还来不及吞“查”解毒，毒性就发作了，神农临死前还紧紧地抱着他的两口袋药草.人们隆重地安葬了神农，尊他为农耕和医药之祖.\n" +
                    "　　当然，上面讲的仅仅是民间的传说.但是，从中也可以看到中华民族物质文明发展的由来.\n" +
                    "\t想认识更多的草药吗，5月同仁堂将开展中药科普活动，欢迎小朋友们参加哦\n" +
                    "    点击下方直接报名哦");
            listItems.add(know);
        }
        return listItems;
    }


    @Override
    public void onClick(View v) {

    }
}
