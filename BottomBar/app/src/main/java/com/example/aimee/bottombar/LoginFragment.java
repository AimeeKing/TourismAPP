package com.example.aimee.bottombar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.example.aimee.bottombar.newServer.request.UserRequest;
import com.example.aimee.bottombar.newServer.response.UserResponse;
import com.example.aimee.bottombar.newServer.utils.ResultCodeDesc;
import com.example.aimee.bottombar.newServer.utils.statics.Global;
import com.example.aimee.bottombar.newServer.utils.statics.NewHttpFactory;

/**
 * Created by Aimee on 2016/3/10.
 */
//大概改好了
public class LoginFragment extends AppCompatActivity {
    private static final int RESULT_LOGIN = 0002;
    private EditText edit_phone;
    private EditText edit_password;
    private Button btn_submit;
    private Button btn_forrget;
    private String phone;
    private String password;
//    private Handler handler;
    private static final int RETRY_INTERVAL =6;
    private int time=RETRY_INTERVAL;

    public Intent i;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_in);
        ImageView backImg = (ImageView) findViewById(R.id.back_img);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        init();

    }

    private void init()
    {
//        handler=new Handler();
        edit_phone = (EditText) findViewById(R.id.username);
        edit_password = (EditText) findViewById(R.id.password);
        btn_submit = (Button) findViewById(R.id.button);
        btn_forrget = (Button) findViewById(R.id.forget);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new Thread(new Runnable() {//把获取验证码的按钮变成无法按下去，并且倒计时
//
//                    @Override
//                    public void run() {
//                        while(time-- > 0) {
//                            handler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    btn_submit.setText("已发送(" +time+"s)");
//                                    btn_submit.setEnabled(false);
//                                    btn_submit.setTextColor(getResources().getColor(R.color.white));
//                                    btn_submit.setBackgroundColor(getResources().getColor(R.color.pressed));
//                                }
//
//                            });
//                            try{
//                                Thread.sleep(1000);
//                            }catch (Exception E)
//                            {
//                                //NOTHING
//                            }
//                        }
//
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                time = RETRY_INTERVAL;
//                                btn_submit.setText("获取验证码");
//                                btn_submit.setEnabled(true);
//                                btn_submit.setBackgroundColor(getResources().getColor(R.color.orange));
//                            }
//                        });
//
//
//                    }
//                }).start();
                phone = edit_phone.getText().toString().equals("")?null:edit_phone.getText().toString();
                password = edit_password.getText().toString().equals("")?null:edit_password.getText().toString();
                if(phone!=null&&password!=null){
//                    System.out.println();
                    Global.mHandler = new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            String result = msg.getData().getString("result");
                            System.out.println("Result:"+result);
                            UserResponse userResponse = JSON.parseObject(result,UserResponse.class);
                            System.out.println("Result:"+JSON.toJSONString(userResponse));

                            //判断是否登录成功  直接获取response里面的成员进行查询
                            if(userResponse.getResultCode().equals(ResultCodeDesc.SUCCESS)!=true){
                                System.out.println("登录失败");
                                i=new Intent();
                                Bundle b=new Bundle();
                            }else{
                                System.out.println("登录成功");
                                try {
//                                            JSONObject jsonObject=new JSONObject(result);
                                            SharedPreferences sh = getSharedPreferences("userinfo",
                                                    Activity.MODE_PRIVATE);
                                            SharedPreferences.Editor editor  = sh.edit();
                                            editor.putString("user_id",userResponse.getCode())
                                                    .putString("password",password)
                                                    .putString("imgurl",userResponse.getLogoImgUrl())
                                                    .putString("user_name",userResponse.getName())
                                                    .putString("user_nickname",userResponse.getNickName())
                                                    .putString("user_image",userResponse.getLogoImgUrl())
                                                    .commit();
                                            i=new Intent();
                                            Bundle b=new Bundle();
                                            b.putString("user_nickname",userResponse.getNickName());//"username","手机用户"+edit_phone.getText().toString()
                                            i.putExtras(b);
                                            setResult(RESULT_LOGIN, i);
                                            finish();
                                            } catch (JSONException e) {
                                            e.printStackTrace();
                                            }
                            }

//                            if(msg.what == Global.SUCCESS){
//                                String result = msg.getData().getString("result");
//                                System.out.println("Result:"+result);
//                                //Toast.makeText(getContext(),"result"+result,Toast.LENGTH_SHORT).show();
////                                switch (NewHttpFactory.CheckResult(result)){
////                                    case Global.IsHRadSuccess:
////                                        break;
////                                    case Global.IsHRbtFail:
////                                        break;
////                                    case Global.NotHR:
////                                        UserResponse usr = (UserResponse) JSON.parseObject(result, UserResponse.class);
////                                        System.out.println("登录成功");
////
////                                        try {
////                                            JSONObject jsonObject=new JSONObject(result);
////                                            String id= jsonObject.getString("user_id");
////                                            SharedPreferences sh = getActivity().getSharedPreferences("userinfo",
////                                                    Activity.MODE_PRIVATE);
////                                            SharedPreferences.Editor editor  = sh.edit();
////                                            editor.putString("user_id",id).commit();
////                                            i=new Intent();
////                                            Bundle b=new Bundle();
////                                            b.putString("username","手机用户"+edit_phone.getText().toString());
////                                            i.putExtras(b);
////                                            getActivity().setResult(RESULT_LOGIN, i);
////                                            getActivity().finish();
////                                            } catch (JSONException e) {
////                                            e.printStackTrace();
////                                            }
////                                        break;
////                                }
//
//                            }else{
//                                System.out.println("登录失败p");
//                            }
                        }
                    };
//                    //params存放数据
//                    RequestParams params = new RequestParams();
//                    //添加手机号
//                    params.add("userName",phone);
//                    //对密码加密
//                    password = ParseMD5.parseStrToMd5U32(password);
//                    //添加加密后的密码
//                    params.add("password",password);
//                    //设置Handler 然后通过params传数据 向服务器发送数据登录
//                    HttpFactory.getUserClient(Global.mHandler).Login(params);
                    //MD5加密我没写
                    //新建request 然后在request里面添加参数
                    UserRequest request = new UserRequest();
                    request.setUserName(phone);
                    request.setPassword(password);
                    //工厂类获取Client对象 进行登录
                    NewHttpFactory.getUserClient().login(request);
                }else{
                    //Toast.makeText(getContext(),"please enter information",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        i=new Intent();
        Bundle b=new Bundle();
        b.putString("user_nickname",null);//"username","手机用户"+edit_phone.getText().toString()
        i.putExtras(b);
        setResult(RESULT_LOGIN, i);
        finish();
    }
}
