package com.example.aimee.bottombar;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.example.aimee.bottombar.newServer.request.UserRequest;
import com.example.aimee.bottombar.newServer.response.UserResponse;
import com.example.aimee.bottombar.newServer.utils.ResultCodeDesc;
import com.example.aimee.bottombar.newServer.utils.statics.Global;
import com.example.aimee.bottombar.newServer.utils.statics.NewHttpFactory;
import com.redbooth.WelcomeCoordinatorLayout;

//待设置的注册界面
public class SinUpActivitySec extends AppCompatActivity {
    WelcomeCoordinatorLayout coordinatorLayout;
    boolean animationReady = false;
    private ValueAnimator backgroundAnimator;
    private TextView hint;
    private String username;
    private String password;
    private String nickName;
    private String childClass;
    private ImageButton skipbackImgBtn;
    private FloatingActionButton skipfloatbutton;
    private EditText phoneNumber;
    private int RESULT_SIGN = 0002;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_sec);
        initView();
        initializeListeners();
        initializeBackgroundTransitions();
    }

    private void initializeListeners() {
        coordinatorLayout.setOnPageScrollListener(new WelcomeCoordinatorLayout.OnPageScrollListener() {
            @Override
            public void onScrollPage(View v, float progress, float maximum) {
                if (!animationReady) {
                    animationReady = true;
                    backgroundAnimator.setDuration((long) maximum);
                }
                backgroundAnimator.setCurrentPlayTime((long) progress);
            }

            @Override
            public void onPageSelected(View v, final int pageSelected) {
                skipbackImgBtn.setVisibility(View.VISIBLE);
                skipfloatbutton.setImageResource(R.drawable.forword);
                if(pageSelected<1)
                    skipbackImgBtn.setVisibility(View.INVISIBLE);
                else
                    if(pageSelected == 4)
                    skipfloatbutton.setImageResource(R.drawable.ok);
                switch (pageSelected) {
                    case 0:
                        hint = (TextView) v.findViewById(R.id.hint);
                        phoneNumber = (EditText) v.findViewById(R.id.phonenumber);
                        phoneNumber.setFocusable(true);
                        phoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                if (hasFocus) {
                                    hint.setVisibility(View.VISIBLE);
                                    ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(hint, "alpha", 0.0f, 1.0f);
                                    animatorAlpha.setDuration(1000);
                                    ObjectAnimator animatortran = ObjectAnimator.ofFloat(hint, "translationY", 200f, 0f);
                                    animatortran.setDuration(1000);
                                    AnimatorSet animatorSet = new AnimatorSet();
                                    animatorSet.play(animatorAlpha).with(animatortran);
                                    animatorSet.start();
                                    Log.i("onFocusChange", "" + hasFocus);
                                } else {
                                    hint.setVisibility(View.INVISIBLE);
                                    Log.i("onFocusChange", "" + hasFocus);
                                    username = ((EditText) v).getText().toString();
                                }
                            }
                        });
                        phoneNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                            @Override
                            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                if(actionId == EditorInfo.IME_ACTION_DONE||actionId == EditorInfo.IME_ACTION_NEXT)
                                {
                                    password = v.getText().toString();
                                    phoneNumber.setFocusable(false);
                                }
                                return false;
                            }
                        });

                        break;
                    case 1:
                        hint = (TextView) v.findViewById(R.id.hint_password);
                        final EditText passwordET = (EditText) v.findViewById(R.id.password);
                        passwordET.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        v.findViewById(R.id.show).setTag(false);//密码不可见
                        passwordET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        v.findViewById(R.id.show).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (v.getTag().equals(true)) {
                                    v.setTag(false);
                                    ((ImageButton) v).setImageResource(R.drawable.notshow);
                                    passwordET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                }
                                else
                                {
                                    v.setTag(true);
                                    ((ImageButton) v).setImageResource(R.drawable.show);
                                    passwordET.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                                }
                            }
                        });
                        passwordET.setFocusable(true);
                        passwordET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                if (hasFocus) {
                                    hint.setVisibility(View.VISIBLE);
                                    ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(hint, "alpha", 0.0f, 1.0f);
                                    animatorAlpha.setDuration(1000);
                                    ObjectAnimator animatortran = ObjectAnimator.ofFloat(hint, "translationY", 200f, 0f);
                                    animatortran.setDuration(1000);
                                    AnimatorSet animatorSet = new AnimatorSet();
                                    animatorSet.play(animatorAlpha).with(animatortran);
                                    animatorSet.start();
                                    Log.i("onFocusChange", "" + hasFocus);
                                } else {
                                    hint.setVisibility(View.INVISIBLE);
                                    Log.i("onFocusChange", "" + hasFocus);
                                    password = ((EditText) v).getText().toString();
                                }
                            }
                       });
                        passwordET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                            @Override
                            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                if(actionId == EditorInfo.IME_ACTION_DONE)
                                {
                                    password = v.getText().toString();
                                    passwordET.setFocusable(false);
                                }
                                return false;
                            }
                        });
                        break;
                    case 2:
                        final EditText nickNameET = (EditText) v.findViewById(R.id.nickname);
                        nickNameET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                if(!hasFocus)
                                {
                                    nickName = ((EditText)v).getText().toString();
                                }
                            }
                        });
                        nickNameET.setFocusable(true);
                        nickNameET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                            @Override
                            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                if(actionId == EditorInfo.IME_ACTION_DONE)
                                {
                                    nickName = v.getText().toString();
                                    nickNameET.setFocusable(false);
                                }
                                return false;
                            }
                        });
                        break;
                    case 3:
                        Spinner ageSpinner = (Spinner) v.findViewById(R.id.age_type);
                        ageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                childClass = getResources().getStringArray(R.array.type)[position];
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                childClass = getResources().getStringArray(R.array.type)[3];
                            }
                        });
                        break;
                    case 4:
                        TextView clickTV = (TextView) v.findViewById(R.id.subtitle);
                        clickTV.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.i("onFocusChange","名字"+username+"\n密码"+password+"\n昵称"+nickName+"\n年龄"+childClass);
                            }
                        });
                }
            }
        });
    }

    private void initializeBackgroundTransitions() {
        final Resources resources = getResources();
        final int colorPage0 = ResourcesCompat.getColor(resources, R.color.page0, getTheme());
        final int colorPage1 = ResourcesCompat.getColor(resources, R.color.page4, getTheme());
        final int colorPage2 = ResourcesCompat.getColor(resources, R.color.page3, getTheme());
        final int colorPage3 = ResourcesCompat.getColor(resources, R.color.page2, getTheme());
        final int colorPage4 = ResourcesCompat.getColor(resources, R.color.page1, getTheme());
        backgroundAnimator = ValueAnimator
                .ofObject(new ArgbEvaluator(),colorPage0, colorPage1, colorPage2, colorPage3, colorPage4);
        backgroundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                coordinatorLayout.setBackgroundColor((int) animation.getAnimatedValue());
            }
        });
    }

    private void initView() {
        coordinatorLayout = (WelcomeCoordinatorLayout) findViewById(R.id.coordinator);
        coordinatorLayout.addPage(
                R.layout.sign_page_0,
                R.layout.sign_page_1,
                R.layout.sign_page_2,
                R.layout.sign_page_3,
                R.layout.sign_page_4
                );
        skipbackImgBtn = (ImageButton) findViewById(R.id.skip);
        skipbackImgBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        coordinatorLayout.setCurrentPage(coordinatorLayout.getPageSelected() - 1, true);
                    }
                }
        );
        skipfloatbutton = (FloatingActionButton) findViewById(R.id.fab);
        skipfloatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(coordinatorLayout.getPageSelected()<4) {
                    coordinatorLayout.setCurrentPage(coordinatorLayout.getPageSelected() + 1, true);
                }
                else {
//                    Toast.makeText(SinUpActivitySec.this,"名字"+username+"\n密码"+password+"\n昵称"+nickName+"\n年龄"+childClass,Toast.LENGTH_SHORT).show();
                    if (username != null && password != null) {
                        Global.mHandler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                String result = msg.getData().getString("result");
                                System.out.println("Result:" + result);
                                UserResponse userResponse = JSON.parseObject(result, UserResponse.class);
                                System.out.println("Result:" + JSON.toJSONString(userResponse));

                                //判断是否登录成功  直接获取response里面的成员进行查询
                                if (userResponse.getResultCode().equals(ResultCodeDesc.SUCCESS) != true) {
                                    System.out.println("注册失败");
                                } else {
                                    System.out.println("注册成功");
                                    try {
//                                            JSONObject jsonObject=new JSONObject(result);
                                        SharedPreferences sh = getSharedPreferences("userinfo",
                                                Activity.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sh.edit();
                                        editor.putString("user_id", userResponse.getCode())
                                                .putString("password",password)
                                                .putString("user_name", userResponse.getName())
                                                .putString("user_nick_name", nickName)
                                                .putString("user_image", userResponse.getLogoImgUrl())
                                                .commit();
                                        Intent i = getIntent();
                                        if(i == null) {
                                            i = new Intent(SinUpActivitySec.this, MainActivity.class);
                                            Bundle b = new Bundle();
                                            b.putString("user_nickname", nickName);//"username","手机用户"+edit_phone.getText().toString()
                                            i.putExtras(b);
                                            startActivity(i);
                                            finish();
                                        }
                                        else
                                        {
                                            Bundle b = new Bundle();
                                            b.putString("user_nickname", nickName);//"username","手机用户"+edit_phone.getText().toString()
                                            i.putExtras(b);
                                            setResult(RESULT_SIGN,i);
                                            finish();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        };
                        UserRequest userRequest = new UserRequest();
                        userRequest.setUserName(username);
                        userRequest.setPassword(password);
                        userRequest.setNickName(nickName);
                        userRequest.setPhone(username);
                        userRequest.setLogoImgUrl("http://hbimg.b0.upaiyun.com/80bc9bfbedf758c1e14f62debd8f2b3880462059432a-sOk5tJ_fw658");//默认头像
                        NewHttpFactory.getUserClient().register(userRequest);
                    }
                }
            }
        });
    }

}
