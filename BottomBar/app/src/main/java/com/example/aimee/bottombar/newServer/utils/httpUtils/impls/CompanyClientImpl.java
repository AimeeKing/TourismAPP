package com.example.aimee.bottombar.newServer.utils.httpUtils.impls;

import android.os.Handler;

import com.example.aimee.bottombar.newServer.request.CompanyRequest;
import com.example.aimee.bottombar.newServer.utils.httpUtils.basics.CompanyClient;
import com.example.aimee.bottombar.newServer.utils.statics.Global;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by TonyJiang on 2016/5/3.
 */
public class CompanyClientImpl implements CompanyClient {
    private final String addUrl = Global.LocalHost+"company/put";
    private final String adjustUrl = Global.LocalHost+"company/update";
    private Handler mHandler;
    private AsyncHttpClient companyClient;
    public CompanyClientImpl(Handler handler){
        mHandler = handler;
        companyClient = new AsyncHttpClient();
        companyClient.setConnectTimeout(5000);
        companyClient.setTimeout(5000);
    }
    @Override
    public void addCompany(CompanyRequest request) {
        companyClient.post(addUrl, request.getParams(), new AsyncHttpResponseHandler() {
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
    public void adjustCompany(CompanyRequest request) {
        companyClient.post(adjustUrl, request.getParams(), new AsyncHttpResponseHandler() {
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
