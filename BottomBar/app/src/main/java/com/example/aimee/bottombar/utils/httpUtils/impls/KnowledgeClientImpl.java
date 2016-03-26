package com.example.aimee.bottombar.utils.httpUtils.impls;

import android.os.Handler;
import android.os.Message;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.example.aimee.bottombar.model.Knowledge;
import com.example.aimee.bottombar.utils.httpUtils.basics.KnowledgeClient;
import com.example.aimee.bottombar.utils.statics.Global;
import com.example.aimee.bottombar.utils.statics.JsonTool;

import cz.msebera.android.httpclient.Header;

/**
 * Created by TonyJiang on 2016/3/26.
 */
public class KnowledgeClientImpl implements KnowledgeClient {
    private Handler mHandler;
    private AsyncHttpClient mClient;
    private final String AddURL = Global.LocalHost+"AddKnowledge";
    private final String DeleteURL = Global.LocalHost+"DeleteKnowledge";
    private final String AdjustURL = Global.LocalHost+"AdjustKnowledge";
    private final String ListURL = Global.LocalHost+"ListKnowledge";

    public KnowledgeClientImpl(Handler handler){
        mHandler = handler;
        mClient = new AsyncHttpClient();
        mClient.setConnectTimeout(5000);
        mClient.setTimeout(5000);
    }
    @Override
    public void AddNLG(Knowledge nlg) {
        RequestParams params = new RequestParams();
        params.add("jsonKnowledge", JsonTool.toJson(nlg));
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
    public void DeleteNLG(Knowledge nlg) {
        RequestParams params = new RequestParams();
        params.add("jsonKnowledge", JsonTool.toJson(nlg));
        mClient.post(DeleteURL, params, new AsyncHttpResponseHandler() {
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
    public void AdjustNLG(Knowledge nlg) {
        RequestParams params = new RequestParams();
        params.add("jsonKnowledge", JsonTool.toJson(nlg));
        mClient.post(AdjustURL, params, new AsyncHttpResponseHandler() {
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
    public void ListNLG() {

        mClient.get(ListURL, new AsyncHttpResponseHandler() {
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
