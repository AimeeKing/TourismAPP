package com.example.aimee.bottombar.newServer.utils.httpUtils.impls;

import android.os.Handler;

import com.example.aimee.bottombar.newServer.request.ZanRequest;
import com.example.aimee.bottombar.newServer.utils.httpUtils.basics.ZanClient;
import com.example.aimee.bottombar.newServer.utils.statics.Global;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by TonyJiang on 2016/5/3.
 */
public class ZanClientImpl implements ZanClient {
    private final String addUrl = Global.LocalHost+"zan/put";
    private final String delUrl = Global.LocalHost+"zan/delete";
    private final String countUrl = Global.LocalHost+"zan/count";
    private final String listUrl = Global.LocalHost+"zan/list";
    private Handler mHandler;
    private AsyncHttpClient zanClient;
    public ZanClientImpl(Handler handler){
        mHandler = handler;
        zanClient = new AsyncHttpClient();
        zanClient.setTimeout(5000);
        zanClient.setConnectTimeout(5000);
    }

    @Override
    public void addZan(ZanRequest request) {
        zanClient.post(addUrl, request.getParams(), new AsyncHttpResponseHandler() {
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
    public void delZan(ZanRequest request) {
        zanClient.post(delUrl, request.getParams(), new AsyncHttpResponseHandler() {
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
    public void getCount(ZanRequest request) {
        zanClient.post(countUrl, request.getParams(), new AsyncHttpResponseHandler() {
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
    public void listZan(ZanRequest request) {
        zanClient.post(listUrl, request.getParams(), new AsyncHttpResponseHandler() {
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
