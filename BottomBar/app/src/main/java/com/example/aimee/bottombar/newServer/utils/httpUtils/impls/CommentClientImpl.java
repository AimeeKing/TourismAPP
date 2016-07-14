package com.example.aimee.bottombar.newServer.utils.httpUtils.impls;

import android.os.Handler;

import com.example.aimee.bottombar.newServer.request.CommentRequest;
import com.example.aimee.bottombar.newServer.utils.httpUtils.basics.CommentClient;
import com.example.aimee.bottombar.newServer.utils.statics.Global;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by TonyJiang on 2016/5/3.
 */
public class CommentClientImpl implements CommentClient {
    private final String addUrl = Global.LocalHost+"comment/put";
    private final String delUrl = Global.LocalHost+"comment/delete";
    private final String listUrl = Global.LocalHost+"comment/list";
    private Handler mHandler ;
    private AsyncHttpClient commentClient;
    public CommentClientImpl(Handler handler){
        mHandler = handler;
        commentClient = new AsyncHttpClient();
        commentClient.setConnectTimeout(5000);
        commentClient.setTimeout(5000);

    }
    @Override
    public void addComment(CommentRequest request) {
        commentClient.post(addUrl, request.getParams(), new AsyncHttpResponseHandler() {
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
    public void delComment(CommentRequest request) {
        commentClient.post(delUrl, request.getParams(), new AsyncHttpResponseHandler() {
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
    public void listComment(CommentRequest request) {
        commentClient.get(listUrl, request.getParams(), new AsyncHttpResponseHandler() {
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
