package com.example.aimee.bottombar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.aimee.bottombar.View.stepView.OnCancelAction;
import com.example.aimee.bottombar.View.stepView.OnFinishAction;
import com.example.aimee.bottombar.View.stepView.SteppersItem;
import com.example.aimee.bottombar.View.stepView.SteppersView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PlanActivity extends AppCompatActivity {
    private SteppersView steppersView;
    private ArrayList<SteppersItem> steps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final EditText input =new EditText(PlanActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                lp.setMargins(10,2,10,2);
                input.setLayoutParams(lp);
                android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(PlanActivity.this);
                dialog.setTitle("添加");
                dialog.setView(input);
                dialog.setIcon(R.drawable.comment_1);
                dialog.setPositiveButton("发布", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SteppersItem item = new SteppersItem();
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        item.setLabel("时间"+df.format(new Date()));
                        item.setSubLabel(input.getText().toString());
                        item.setPositiveButtonEnable(true);
                        steps.add(item);
                        steppersView.setItems(steps);
                        steppersView.build();
                    }
                });
                dialog.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                dialog.show();
            }
        });
    }

    private void initView() {
        SteppersView.Config steppersViewConfig = new SteppersView.Config();
        steppersViewConfig.setOnFinishAction(new OnFinishAction() {
            @Override
            public void onFinish() {
                System.out.println("planActivity"+"onFinished!");
            }
        });
        steppersViewConfig.setOnCancelAction(new OnCancelAction() {
            @Override
            public void onCancel() {
                System.out.println("planActivity"+"onFinished!");
            }
        });
        steppersViewConfig.setFragmentManager(getSupportFragmentManager());
        //item后面要改成基于网络的
        steps = new ArrayList<>();
        int i = 0;
        String des[] = new String[]{"要报名胡庆余堂"};
        while(i < des.length)
        {
            final SteppersItem item = new SteppersItem();
            item.setLabel("时间"+"5月"+(i*6+6)+"日");
            item.setSubLabel(des[i]);
            item.setPositiveButtonEnable(true);
            steps.add(item);
            i++;
        }
        steppersView = (SteppersView) findViewById(R.id.steppersView);
        steppersView.setConfig(steppersViewConfig);
        steppersView.setItems(steps);
        steppersView.build();
    }

}
