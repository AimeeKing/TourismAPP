package com.example.aimee.bottombar.newServer.utils.httpUtils.impls;

import android.os.Handler;

import com.example.aimee.bottombar.newServer.request.PurchaseRequest;
import com.example.aimee.bottombar.newServer.utils.httpUtils.basics.PurchaseClient;
import com.example.aimee.bottombar.newServer.utils.statics.Global;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by TonyJiang on 2016/5/3.
 */
public class PurchaseClientImpl implements PurchaseClient {
    private final String addUrl = Global.LocalHost+"purchase/put";
    private final String delUrl = Global.LocalHost+"purchase/delete";
    private final String listUrl = Global.LocalHost+"purchase/list";
    private final String adjustUrl = Global.LocalHost+"purchase/update";
    private Handler mHandler;
    private AsyncHttpClient purchaseClient;
    public PurchaseClientImpl(Handler handler){
        mHandler = handler;
        purchaseClient=new AsyncHttpClient();
        purchaseClient.setConnectTimeout(5000);
        purchaseClient.setTimeout(5000);
    }
    @Override
    public void addPurchase(PurchaseRequest request) {
        purchaseClient.post(addUrl, request.getParams(), new AsyncHttpResponseHandler() {
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
    public void delPurchase(PurchaseRequest request) {
        purchaseClient.post(delUrl, request.getParams(), new AsyncHttpResponseHandler() {
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
    public void listPurchase(PurchaseRequest request) {
        purchaseClient.get(listUrl, request.getParams(), new AsyncHttpResponseHandler() {
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
    public void adjustPurchase(PurchaseRequest request) {
        purchaseClient.post(adjustUrl, request.getParams(), new AsyncHttpResponseHandler() {
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
