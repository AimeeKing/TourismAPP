package com.example.aimee.bottombar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.redbooth.WelcomeCoordinatorLayout;

public class WelcomeActivity extends AppCompatActivity {
    private WelcomeCoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
        initializeListeners();

    }

    private void initializeListeners() {
        coordinatorLayout.setOnPageScrollListener(new WelcomeCoordinatorLayout.OnPageScrollListener() {
            @Override
            public void onScrollPage(View v, float progress, float maximum) {
            }

            @Override
            public void onPageSelected(View v, int pageSelected) {
                if(3 == pageSelected)
                {
                    Button signBtn = (Button) v.findViewById(R.id.sign);
                    Button loginBtn = (Button) v.findViewById(R.id.login);
                    View.OnClickListener clickIntent = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent;
                            if(R.id.sign == v.getId()) {
                                intent = new Intent(WelcomeActivity.this, SinUpActivitySec.class);
                                startActivity(intent);
                            }
                        }
                    };
                    signBtn.setOnClickListener(clickIntent);
                    loginBtn.setOnClickListener(clickIntent);
                }
            }
        });


    }


    private void initView() {
        coordinatorLayout = (WelcomeCoordinatorLayout) findViewById(R.id.welcome_coordinator);
        coordinatorLayout.addPage(R.layout.welcome_1,
                R.layout.welcome_2,
                R.layout.welcome_3,
                R.layout.welcome_4
        );
        Button skipImgBtn = (Button) findViewById(R.id.skip);
        skipImgBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        coordinatorLayout.setCurrentPage(coordinatorLayout.getNumOfPages() - 1, true);
                    }
                }
        );
    }
}
