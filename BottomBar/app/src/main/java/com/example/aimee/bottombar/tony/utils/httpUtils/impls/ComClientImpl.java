package com.example.aimee.bottombar.tony.utils.httpUtils.impls;

import android.os.Handler;

import com.example.aimee.bottombar.tony.utils.httpUtils.basics.ComClient;
import com.example.aimee.bottombar.tony.utils.statics.Global;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Aimee on 2016/4/10.
 */
public class ComClientImpl implements ComClient {
    protected String getURL= Global.LocalHost+"ListComment";
    protected String addURL = Global.LocalHost+"AddComment";
    protected Handler mhandler;
    protected AsyncHttpClient myClient;

    public ComClientImpl(Handler mhandler) {
        this.mhandler = mhandler;
        myClient=new AsyncHttpClient();
        myClient.setConnectTimeout(50000);
        myClient.setTimeout(5000);
    }

    @Override
    public void getComment(String activity_id) {
     RequestParams params = new RequestParams();
        params.add("to_id",activity_id);
        myClient.post(getURL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                mhandler.sendMessage(Global.DealFinal(bytes, Global.SUCCESS));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                mhandler.sendEmptyMessage(Global.FAIL);
            }
        });
    }

    @Override
    public void addComment(RequestParams params) {
        myClient.post(getURL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                mhandler.sendMessage(Global.DealFinal(bytes, Global.SUCCESS));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                mhandler.sendEmptyMessage(Global.FAIL);
            }
        });
    }
}
