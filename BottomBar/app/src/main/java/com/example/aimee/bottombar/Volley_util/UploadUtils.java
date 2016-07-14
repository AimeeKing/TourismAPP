package com.example.aimee.bottombar.Volley_util;


import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.example.aimee.bottombar.newServer.request.UserRequest;
import com.example.aimee.bottombar.newServer.response.Response;
import com.example.aimee.bottombar.newServer.response.UserResponse;
import com.example.aimee.bottombar.newServer.utils.statics.Global;
import com.example.aimee.bottombar.newServer.utils.statics.NewHttpFactory;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Aimee on 2016/6/4.
 */
public class UploadUtils {

//    private String userCode;
//
//    public UploadUtils(String userCode) {
//        this.userCode = userCode;
//    }

    public static void uploadFile(String filePath, final String userName,final String password)
    {
        //服务器端地址
        String url = Global.LocalHost+"photo/addAction";
        //手机端要上传的文件，首先要保存你手机上存在该文件


        AsyncHttpClient httpClient = new AsyncHttpClient();

        RequestParams param = new RequestParams();
        try
        {
            param.put("studentPhoto", new File(filePath));
//            param.put("content", "liucanwen");

            httpClient.post(url, param, new AsyncHttpResponseHandler()
            {
                @Override
                public void onStart()
                {
                    super.onStart();
                    System.out.println("图片上传开始");
                }

                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {

                    System.out.println("图片上传成功");
                    Response JsonResponse = JSON.parseObject(bytes,Response.class);
                    String imgurl = JsonResponse.getMsg();
                    UserRequest userRequest = new UserRequest();
                    System.out.println("图片地址"+Global.LocalHost+"img/"+imgurl);
                    userRequest.setLogoImgUrl(Global.LocalHost+"img/"+imgurl);
                    userRequest.setUserName(userName);
                    userRequest.setPassword(password);
                    Global.mHandler=new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            if(msg.what == Global.SUCCESS){
                                String result = msg.getData().getString("result");
                                System.out.println("更新信息成功"+result);
                                UserResponse user= JSON.parseObject(result, UserResponse.class);
                            }else{
                                System.out.println("获取信息失败");
                            }
                        }
                    };
                    NewHttpFactory.getUserClient().search(userRequest);
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    System.out.println("图片上传失败");
                }


            });

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
            //Toast.makeText(MainActivity.this, "上传文件不存在！", 1000).show();
            System.out.println("上传成功");
        }
    }
}
