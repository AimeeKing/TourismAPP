package com.example.aimee.bottombar.utils.httpUtils.impls;

import android.os.Handler;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.example.aimee.bottombar.model.Comment;
import com.example.aimee.bottombar.utils.httpUtils.basics.CommentClient;
import com.example.aimee.bottombar.utils.statics.Global;
import com.example.aimee.bottombar.utils.statics.JsonTool;

import org.w3c.dom.ls.LSException;

import cz.msebera.android.httpclient.Header;

/**
 * Created by TonyJiang on 2016/3/26.
 */
public class CommentClientImpl implements CommentClient{
    private final String AddURL = Global.LocalHost+"AddComment";
    private final String DeleteUrl = Global.LocalHost+"DeleteComment";
    private final String ListUrl = Global.LocalHost+"ListComment";
    private Handler mHandler;
    private AsyncHttpClient mClient;
    public CommentClientImpl(Handler handler){
        mHandler = handler;
        mClient = new AsyncHttpClient();
        mClient.setTimeout(5000);
        mClient.setConnectTimeout(5000);
    }
    @Override
    public void AddComment(Comment comt) {
        RequestParams params = new RequestParams();
        params.add("jsonComm", JsonTool.toJson(comt));
        mClient.post(AddURL, params, new AsyncHttpResponseHandler() {
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
    public void DeleteComment(Comment comt) {
        RequestParams params = new RequestParams();
        params.add("jsonComm",JsonTool.toJson(comt));
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
    public void ListComment(Comment comt) {
        RequestParams params = new RequestParams();
        params.add("jsonComm",JsonTool.toJson(comt));
        mClient.get(ListUrl, params, new AsyncHttpResponseHandler() {
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
