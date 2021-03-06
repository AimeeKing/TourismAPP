package com.example.aimee.bottombar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.example.aimee.bottombar.newServer.request.UserRequest;
import com.example.aimee.bottombar.newServer.response.UserResponse;
import com.example.aimee.bottombar.newServer.utils.ResultCodeDesc;
import com.example.aimee.bottombar.newServer.utils.statics.Global;
import com.example.aimee.bottombar.newServer.utils.statics.NewHttpFactory;

import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Aimee on 2016/3/10.
 */
//注册界面总是害怕有错
public class SignUpFragment extends Fragment {
    private static final int RESULT_LOGIN = 0002;
    private EditText edit_phone;
    private EditText edit_password;
    private EditText edit_code;
    private Button btn_submit;
    private Button btn_get;
    private String phone;
    private String password;
    private String code;
    private String APPKEY="10e52cbe65b78";
    private String APPSECRET = "fc7f68ad5b308b40ac4b7e2399a5a894";//这两个是Mob.com的短信验证的appkey到时候集成的时候需要更改
    private Handler handler;
    private EventHandler eh;
    private static final int RETRY_INTERVAL =6;
    private int time=RETRY_INTERVAL;
    private int btn_height;
    private Intent i;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View logn=inflater.inflate(R.layout.login,container,false);
        init(logn);
        i=new Intent();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code = edit_code.getText().toString().trim();
                phone = edit_phone.getText().toString().equals("")?null:edit_phone.getText().toString();
                password = edit_password.getText().toString().equals("")?null:edit_password.getText().toString();
//                if(phone!=null && password!=null){
//                    Handler mHandler = new Handler(){
//                        @Override
//                        public void handleMessage(Message msg) {
//                            super.handleMessage(msg);
//                            String result = msg.getData().getString("result");
//                            System.out.println("Result:"+result);
//                            UserResponse userResponse = JSON.parseObject(result,UserResponse.class);
//                            System.out.println("Result:"+JSON.toJSONString(userResponse));
//
//                            //判断是否登录成功  直接获取response里面的成员进行查询
//                            if(userResponse.getResultCode().equals(ResultCodeDesc.SUCCESS)!=true){
//                                System.out.println("登录失败");
//                            }else{
//                                System.out.println("登录成功");
//                                try {
////                                            JSONObject jsonObject=new JSONObject(result);
//                                    SharedPreferences sh = getActivity().getSharedPreferences("userinfo",
//                                            Activity.MODE_PRIVATE);
//                                    SharedPreferences.Editor editor  = sh.edit();
//                                    editor.putString("user_id",userResponse.getCode())
//                                            .putString("user_name",userResponse.getName())
//                                            .putString("user_nick_name",userResponse.getNickName())
//                                            .putString("user_image",userResponse.getLogoImgUrl())
//                                            .commit();
//                                    i=new Intent();
//                                    Bundle b=new Bundle();
//                                    b.putString("user_nickname",userResponse.getNickName());//"username","手机用户"+edit_phone.getText().toString()
//                                    i.putExtras(b);
//                                    getActivity().setResult(RESULT_LOGIN, i);
//                                    getActivity().finish();
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    };
//                    UserRequest userRequest = new UserRequest();
//                    userRequest.setUserName(phone);
//                    userRequest.setPassword(password);
//                    NewHttpFactory.getUserClient().register(userRequest);
////                    Bundle b=new Bundle();
////                    b.putString("user_nickname","手机用户"+edit_phone.getText().toString());
////                    i.putExtras(b);
////                    getActivity().setResult(RESULT_LOGIN, i);
////                    getActivity().finish();
//                }
                SMSSDK.submitVerificationCode("86",phone,code);
            }
        });

        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = edit_phone.getText().toString();
                SMSSDK.getVerificationCode("86", phone, new OnSendMessageHandler() {
                    @Override
                    public boolean onSendMessage(String s, String s1) {
                        return false;
                    }
                });
            }
        });
        return logn;
    }
    private void init(View logn)
    {
        handler=new Handler();
        edit_phone=(EditText)logn.findViewById(R.id.username_log);
        edit_password=(EditText)logn.findViewById(R.id.password_log);
        edit_code=(EditText)logn.findViewById(R.id.makesure);
        btn_submit=(Button)logn.findViewById(R.id.button_logn);
        btn_get=(Button)logn.findViewById(R.id.require_code);
        btn_height=btn_get.getHeight();
         SMSSDK.initSDK(this.getActivity(), APPKEY, APPSECRET);
        eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功,校验验证码，返回校验的手机和国家代码
                        password = edit_password.getText().toString();
                        //传值给服务器
                        System.out.print("验证成功");

                        if (phone != null && password != null) {
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
                                        System.out.println("注册成功");
                                    } else {
                                        System.out.println("注册失败");
                                        try {
//                                            JSONObject jsonObject=new JSONObject(result);
                                            SharedPreferences sh = getActivity().getSharedPreferences("userinfo",
                                                    Activity.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sh.edit();
                                            editor.putString("user_id", userResponse.getCode())
                                                    .putString("user_name", userResponse.getName())
                                                    .putString("user_nick_name", userResponse.getNickName())
                                                    .putString("user_image", userResponse.getLogoImgUrl())
                                                    .commit();
                                            i = new Intent();
                                            Bundle b = new Bundle();
                                            b.putString("user_nickname", userResponse.getNickName());//"username","手机用户"+edit_phone.getText().toString()
                                            i.putExtras(b);
                                            getActivity().setResult(RESULT_LOGIN, i);
                                            getActivity().finish();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            };
                            UserRequest userRequest = new UserRequest();
                            userRequest.setUserName(phone);
                            userRequest.setPassword(password);
                            NewHttpFactory.getUserClient().register(userRequest);
//                    Handler mHandler = new Handler(){
//                        @Override
//                        public void handleMessage(Message msg) {
//                            super.handleMessage(msg);
//                            if(msg.what == Global.SUCCESS){
//                                String result = msg.getData().getString("result");
//                                switch (HttpFactory.CheckResult(result)){
//                                    case Global.IsHRadSuccess:
//                                        System.out.println("注册成功");
//                                        break;
//                                    case Global.IsHRbtFail:
//                                        System.out.println("注册失败");
//                                        break;
//                                }
//                            }else{
//                                System.out.println("注册失败");
//                            }
//                        }
//                    };
//                    //params存放数据
//                    RequestParams params = new RequestParams();
//                    //添加手机号
//                    params.add("userName",phone);
//                    //对密码加密
//                    password = ParseMD5.parseStrToMd5U32(password);
//                    //添加加密后的密码
//                    params.add("password",password);
//                    //设置Handler 然后通过params传数据 向服务器发送数据进行注册
//                    HttpFactory.getUserClient(mHandler).SignUp(params);
                }

                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功,请求发送验证码，无返回
                        System.out.print("获取验证码成功");
                   /*     new Thread(new Runnable() {//把获取验证码的按钮变成无法按下去，并且倒计时

                            @Override
                            public void run() {
                                while(time-- > 0) {
                                    handler.post(new Runnable() {
                                        @Override
                                       public void run() {
                                            btn_get.setHeight(btn_height);
                                            btn_get.setText("已发送(" +time+"s)");
                                            btn_get.setEnabled(false);
                                            btn_get.setTextColor(getResources().getColor(R.color.white));
                                            btn_get.setBackgroundColor(getResources().getColor(R.color.pressed));
                                        }

                                    });
                                    try{
                                        Thread.sleep(1000);
                                    }catch (Exception E)
                                    {
                                    //NOTHING
                                    }
                                   }
                             // }

                                handler.post(new Runnable() {
                                   @Override
                                    public void run() {
                                        time = RETRY_INTERVAL;
                                        btn_get.setText("获取验证码");
                                        btn_get.setEnabled(true);
                                        btn_get.setBackgroundColor(getResources().getColor(R.color.orange));
                                   }
                               });


                           }
                       }).start();*/

                }else
                    {
                        ((Throwable)data).printStackTrace();
                    }
                }
            }
        };
        SMSSDK.registerEventHandler(eh);//注册短信回调
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
       SMSSDK.unregisterAllEventHandler();//registerEventHandler用来往SMSSDK中注册一个事件接收器，SMSSDK允许开发者注册任意数量的接收器，所有接收器都会在事件 被触发时收到消息。
    }

}
