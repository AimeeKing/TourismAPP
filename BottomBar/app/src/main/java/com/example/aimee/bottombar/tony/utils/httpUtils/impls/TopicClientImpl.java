package com.example.aimee.bottombar.tony.utils.httpUtils.impls;

import android.os.Handler;

import com.example.aimee.bottombar.tony.utils.httpUtils.basics.TopicClient;
import com.example.aimee.bottombar.tony.utils.statics.Global;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Aimee on 2016/4/7.
 */
public class TopicClientImpl implements TopicClient {
    //1 写上URLAddActivity
    private final String addTopic = Global.LocalHost+"AddTopic";
    private final String getTopic = Global.LocalHost+"ListTopic";
   // private final String zan = Global.LocalHost+"servlet/SearchActivity";
    //2 我写个handler
    private Handler mHandler;
    //3 写个cleint
    private AsyncHttpClient myActClient;

    public TopicClientImpl(Handler mHandler) {
        this.mHandler = mHandler;
        myActClient = new AsyncHttpClient();
        myActClient.setTimeout(5000);
        myActClient.setConnectTimeout(5000);
    }
    @Override
    public void addTopic(String TopicJson) {
        RequestParams params = new RequestParams();
        params.add("jsonTopic",TopicJson);
        myActClient.post(addTopic, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        mHandler.sendMessage(Global.DealFinal(bytes, Global.SUCCESS));
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        mHandler.sendMessage(Global.DealFinal(bytes, Global.FAIL));
                    }
                }
        );
    }

    @Override
    public void getTopic() {
        myActClient.get(getTopic, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                mHandler.sendMessage(Global.DealFinal(bytes, Global.SUCCESS));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                mHandler.sendMessage(Global.DealFinal(bytes, Global.FAIL));
            }
        });
    }
}
