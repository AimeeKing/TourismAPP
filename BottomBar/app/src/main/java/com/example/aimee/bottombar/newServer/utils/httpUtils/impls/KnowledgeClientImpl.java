package com.example.aimee.bottombar.newServer.utils.httpUtils.impls;

import android.os.Handler;

import com.example.aimee.bottombar.newServer.request.KnowledgeRequest;
import com.example.aimee.bottombar.newServer.utils.httpUtils.basics.KnowledgeClient;
import com.example.aimee.bottombar.newServer.utils.statics.Global;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by TonyJiang on 2016/5/3.
 */
public class KnowledgeClientImpl implements KnowledgeClient {
    private final String addUrl = Global.LocalHost+"knowledge/put";
    private final String delUrl = Global.LocalHost+"knowledge/delete";
    private final String listUrl = Global.LocalHost+"knowledge/list";
    private final String adjustUrl = Global.LocalHost+"knowledge/update";

    private Handler mHandler;
    private AsyncHttpClient knowledgeClient;
    public KnowledgeClientImpl(Handler handler){
        mHandler = handler;
        knowledgeClient = new AsyncHttpClient();
        knowledgeClient.setConnectTimeout(5000);
        knowledgeClient.setTimeout(5000);
    }
    @Override
    public void addKnowledge(KnowledgeRequest request) {
        knowledgeClient.post(addUrl, request.getParams(), new AsyncHttpResponseHandler() {
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
    public void delKnowledge(KnowledgeRequest request) {
        knowledgeClient.post(delUrl, request.getParams(), new AsyncHttpResponseHandler() {
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
    public void listKnowledge(KnowledgeRequest request) {
        knowledgeClient.get(listUrl, request.getParams(), new AsyncHttpResponseHandler() {
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
    public void adjustKnowledge(KnowledgeRequest request) {
        knowledgeClient.post(adjustUrl, request.getParams(), new AsyncHttpResponseHandler() {
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
