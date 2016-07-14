package com.example.aimee.bottombar.newServer.utils.httpUtils.impls;

import android.os.Handler;

import com.example.aimee.bottombar.newServer.request.UserRequest;
import com.example.aimee.bottombar.newServer.utils.httpUtils.basics.UserClient;
import com.example.aimee.bottombar.newServer.utils.statics.Global;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

/**
 * Created by TonyJiang on 2016/5/3.
 */
public class UserClientImpl implements UserClient {
    private final String loginUrl = Global.LocalHost+"user/login";
    private final String registerUrl = Global.LocalHost+"user/put";
    private final String adjustUrl = Global.LocalHost+"user/update";
    private final String searchUrl = Global.LocalHost+"user/search";
    private Handler mHandler;
    private AsyncHttpClient userClient;

    public UserClientImpl(Handler handler){
        mHandler=handler;
        userClient= new AsyncHttpClient();
        userClient.setTimeout(5000);
        userClient.setConnectTimeout(5000);
    }
    @Override
    public void login(UserRequest request) {
        RequestParams params = new RequestParams();
        params.add("userName",request.getUserName());
        params.add("password",request.getPassword());
        userClient.post(loginUrl, request.getParams(), new AsyncHttpResponseHandler() {
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
    public void register(UserRequest request) {
        userClient.post(registerUrl, request.getParams(), new AsyncHttpResponseHandler() {
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
    public void update(UserRequest request) {
        userClient.post(adjustUrl, request.getParams(), new AsyncHttpResponseHandler() {
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
    public void search(UserRequest request) {
        userClient.post(searchUrl, request.getParams(), new AsyncHttpResponseHandler() {
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
