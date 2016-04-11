package com.example.aimee.bottombar.tony.utils.httpUtils.impls;

import android.os.Handler;

import com.example.aimee.bottombar.tony.utils.httpUtils.basics.ActClient;
import com.example.aimee.bottombar.tony.utils.statics.Global;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Aimee on 2016/4/6.
 */
public class ActClientImpl implements ActClient {
    //1 写上URLAddActivity
    private final String addActUrl = Global.LocalHost+"AddActivity";
    private final String getActUrl = Global.LocalHost+"GetAcitivities";
    private final String searchUrl = Global.LocalHost+"servlet/SearchActivity";
    //2 我写个handler
    private Handler mHandler;
    //3 写个cleint
    private AsyncHttpClient myActClient;

    public ActClientImpl(Handler mHandler) {
        this.mHandler = mHandler;
        myActClient = new AsyncHttpClient();
        myActClient.setTimeout(5000);
        myActClient.setConnectTimeout(5000);
    }
    @Override
    public void addAct(String actJson) {
        RequestParams params = new RequestParams();
        params.add("jsonAct", actJson);
        myActClient.post(addActUrl, params, new AsyncHttpResponseHandler() {//params数据
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                System.out.println(addActUrl);

                mHandler.sendMessage(Global.DealFinal(bytes, Global.SUCCESS));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                System.out.println(addActUrl);
                mHandler.sendEmptyMessage(Global.FAIL);
            }
        });
    }

    @Override
    public void searchAct(String name,String value) {
        RequestParams params = new RequestParams();
        params.add("value",value);
        params.add("name",name);
        myActClient.get(searchUrl, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                System.out.println(searchUrl);

                mHandler.sendMessage(Global.DealFinal(bytes, Global.SUCCESS));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                System.out.println(searchUrl);

                mHandler.sendMessage(Global.DealFinal(bytes, Global.SUCCESS));
            }
        });
    }

    @Override
    public void getAct() {
        myActClient.get(getActUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                System.out.println(getActUrl);

                mHandler.sendMessage(Global.DealFinal(bytes, Global.SUCCESS));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                System.out.println(getActUrl);

                mHandler.sendEmptyMessage(Global.FAIL);
            }
        });
    }
}
