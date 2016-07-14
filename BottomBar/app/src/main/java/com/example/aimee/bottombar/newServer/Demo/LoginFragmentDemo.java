package com.example.aimee.bottombar.newServer.Demo;

import android.content.Intent;
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
import com.example.aimee.bottombar.R;
import com.example.aimee.bottombar.newServer.request.UserRequest;
import com.example.aimee.bottombar.newServer.response.UserResponse;
import com.example.aimee.bottombar.newServer.utils.ResultCodeDesc;
import com.example.aimee.bottombar.newServer.utils.statics.Global;
import com.example.aimee.bottombar.newServer.utils.statics.NewHttpFactory;

/**
 * Created by Aimee on 2016/3/10.
 */
public class LoginFragmentDemo extends Fragment {

    private static final int RESULT_LOGIN = 0002;
    private EditText edit_phone;
    private EditText edit_password;
    private Button btn_submit;
    private Button btn_forrget;
    private String phone;
    private String password;
    private Handler handler;
    private static final int RETRY_INTERVAL =6;
    private int time=RETRY_INTERVAL;

    public Intent i;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View sign=inflater.inflate(R.layout.login_in,container,false);
        init(sign);

        return sign;
    }

    private void init(View sign)
    {
        handler=new Handler();
        edit_phone = (EditText) sign.findViewById(R.id.username);
        edit_password = (EditText) sign.findViewById(R.id.password);
        btn_submit = (Button) sign.findViewById(R.id.button);
        btn_forrget = (Button) sign.findViewById(R.id.forget);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phone = edit_phone.getText().toString().equals("")?null:edit_phone.getText().toString();
                password = edit_password.getText().toString().equals("")?null:edit_password.getText().toString();
                if(phone!=null&&password!=null){

                    Global.mHandler = new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            if(msg.what == Global.SUCCESS){
                                String result = msg.getData().getString("result");
                                System.out.println("Result:"+result);
                                UserResponse userResponse =  JSON.parseObject(result,UserResponse.class);
                                System.out.println("Result:"+JSON.toJSONString(userResponse));

                                //判断是否登录成功  直接获取response里面的成员进行查询
                                if(userResponse.getResultCode().equals(ResultCodeDesc.SUCCESS)!=true){
                                    System.out.println("登录失败");
                                }else{
                                    System.out.println("登录成功");
                                }

                                //判断完之后的跳转被我删掉了。。。
                            }else{
                                System.out.println("登录失败");
                            }
                        }
                    };
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

}
