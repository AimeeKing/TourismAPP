package com.example.aimee.bottombar.utils.httpUtils.impls;

import android.os.Handler;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.example.aimee.bottombar.model.Tel;
import com.example.aimee.bottombar.model.User;
import com.example.aimee.bottombar.utils.httpUtils.basics.TelClient;
import com.example.aimee.bottombar.utils.statics.Global;
import com.example.aimee.bottombar.utils.statics.JsonTool;

import cz.msebera.android.httpclient.Header;

/**
 * Created by TonyJiang on 2016/3/26.
 */
public class TelClientImpl implements TelClient{
    private final String AddUrl = Global.LocalHost+"AddTel";
    private final String DeleteUrl = Global.LocalHost+"DeleteTel";
    private final String ListUrl =  Global.LocalHost+"ListTel";
    private AsyncHttpClient mClient;
    private Handler mHandler;
    public TelClientImpl(Handler handler){
        mHandler = handler;
        mClient = new AsyncHttpClient();
        mClient.setConnectTimeout(5000);
        mClient.setTimeout(5000);
    }

    @Override
    public void AddTel(Tel tel) {
        RequestParams params = new RequestParams();
        params.add("telJson", JsonTool.toJson(tel));
        mClient.post(AddUrl, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                mHandler.sendMessage(Global.DealFinal(bytes,Global.SUCCESS));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                mHandler.sendMessage(Global.DealFinal(bytes,Global.FAIL));
            }
        });
    }

    @Override
    public void DeleteTel(Tel tel) {
        RequestParams params = new RequestParams();
        params.add("telJson",JsonTool.toJson(tel));
        mClient.post(DeleteUrl, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                mHandler.sendMessage(Global.DealFinal(bytes,Global.SUCCESS));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                mHandler.sendMessage(Global.DealFinal(bytes,Global.FAIL));
            }
        });
    }

    @Override
    public void ListTel(User user) {
        RequestParams params = new RequestParams();
        params.add("userName",user.getUserName());
        mClient.post(ListUrl, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                mHandler.sendMessage(Global.DealFinal(bytes,Global.SUCCESS));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                mHandler.sendMessage(Global.DealFinal(bytes,Global.FAIL));
            }
        });
    }


}
