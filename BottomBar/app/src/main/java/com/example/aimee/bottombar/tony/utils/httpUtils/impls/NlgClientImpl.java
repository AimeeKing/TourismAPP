package com.example.aimee.bottombar.tony.utils.httpUtils.impls;

import android.os.Handler;

import com.example.aimee.bottombar.tony.utils.httpUtils.basics.NlgClient;
import com.example.aimee.bottombar.tony.utils.statics.Global;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Aimee on 2016/4/5.
 */
public class NlgClientImpl implements NlgClient {
    //1 写上URL
    private final String addNlgUrl= Global.LocalHost+"AddKnowledge";
    //2 我写个handler
    private Handler mHandler;
    //3 写个cleint
    private AsyncHttpClient myNlgClient;

    public NlgClientImpl(Handler mHandler) {
        this.mHandler = mHandler;
        myNlgClient = new AsyncHttpClient();
        myNlgClient.setTimeout(5000);
        myNlgClient.setConnectTimeout(5000);
    }

    @Override
    public void addNlg(String nlgJson) {
        RequestParams params = new RequestParams();
        params.add("jsonKnowledge", nlgJson);
        myNlgClient.get(addNlgUrl, params, new AsyncHttpResponseHandler() {
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
    public void getNlg(String nlgJson) {
        RequestParams params = new RequestParams();
        params.add("jsonKnowledge",nlgJson);
        myNlgClient.get(addNlgUrl, params, new AsyncHttpResponseHandler() {
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
