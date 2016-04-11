package com.example.aimee.bottombar.tony.utils.httpUtils.impls;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.aimee.bottombar.tony.utils.httpUtils.basics.UserClient;
import com.example.aimee.bottombar.tony.utils.statics.Global;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

/**
 * Created by TonyJiang on 2016/3/26.
 */
public class UserClientImpl implements UserClient {
    private Handler mHandler;//传数据
    private String urlUserLogin = Global.LocalHost+"Login";//登录用的连接
    private String urlUserSign = Global.LocalHost+"SignUp";//注册用的连接
    private String urlAdminLogin = Global.LocalHost+"AdminLogin";
    private String urlSearchUser = Global.LocalHost+"SearchUser";
    private AsyncHttpClient myClient;//创建AsyncHttpClient对象，他进行get和post

    public void setmHandler(Handler handler){
        mHandler = handler;
    }

    public UserClientImpl(Handler handler){//构造函数设置handler
        myClient = new AsyncHttpClient();
        myClient.setTimeout(5000);
        myClient.setConnectTimeout(5000);
        mHandler = handler;
    }

    /**
     * 登录
     * @param params
     * params.add("userName","Fucker")
     * params.add("password","mypassword")
     */
    @Override
    public void Login(final RequestParams params) {
        myClient.post(urlUserLogin, params, new AsyncHttpResponseHandler() {//params数据
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                System.out.println(urlUserLogin);

                mHandler.sendMessage(Global.DealFinal(bytes, Global.SUCCESS));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                System.out.println(urlUserLogin);
                mHandler.sendEmptyMessage(Global.FAIL);
            }
        });
    }
    //结果处理函数
    private Message DealFinal(byte[] bytes,int resultType){
        String result = new String(bytes);
        Bundle bundle = new Bundle();
        Message msg = new Message();
        bundle.putString("result",result);
        msg.what=resultType;
        msg.setData(bundle);
        return msg;
    }

    /**
     * 注册
     * @param params
     * params.add("userName","Fucker")
     * params.add("password","mypassword")
     */
    @Override
    public void SignUp(RequestParams params) {
        myClient.post(urlUserSign, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                mHandler.sendMessage(Global.DealFinal(bytes, Global.SUCCESS));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                mHandler.sendEmptyMessage(Global.FAIL);
            }
        });
    }

    /**
     * 管理员登录
     * @param params
     * params.add("userName","Fucker")
     * params.add("password","mypassword")
     */
    @Override
    public void AdminLogin(RequestParams params) {
        myClient.post(urlAdminLogin, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                mHandler.sendMessage(Global.DealFinal(bytes, Global.SUCCESS));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                mHandler.sendEmptyMessage(Global.FAIL);
            }
        });
    }

    @Override
    public void SearchUser(RequestParams params) {
        myClient.post(urlSearchUser, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                mHandler.sendMessage(Global.DealFinal(bytes, Global.SUCCESS));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                mHandler.sendEmptyMessage(Global.FAIL);
            }
        });
    }
}
