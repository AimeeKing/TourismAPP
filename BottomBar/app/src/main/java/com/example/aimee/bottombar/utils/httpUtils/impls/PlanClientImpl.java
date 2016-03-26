package com.example.aimee.bottombar.utils.httpUtils.impls;

import android.os.Handler;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.example.aimee.bottombar.model.Plan;
import com.example.aimee.bottombar.utils.httpUtils.basics.PlanClient;
import com.example.aimee.bottombar.utils.statics.Global;
import com.example.aimee.bottombar.utils.statics.JsonTool;

import cz.msebera.android.httpclient.Header;

/**
 * Created by TonyJiang on 2016/3/26.
 */
public class PlanClientImpl implements PlanClient {
    private final String AddUrl = Global.LocalHost+"AddPlan";
    private final String AddPeopleUrl = Global.LocalHost+"AddPeople";
    private final String AdjustUrl = Global.LocalHost+"AdjustPlan";
    private final String DeleteUrl = Global.LocalHost+"DeletePlan";
    private final String ListUrl = Global.LocalHost+"ListPlan";
    private Handler mHandler;
    private AsyncHttpClient mClient;
    public PlanClientImpl(Handler handler){
        mHandler = handler;
        mClient = new AsyncHttpClient();
        mClient.setConnectTimeout(5000);
        mClient.setTimeout(5000);
    }
    @Override
    public void AddPlan(Plan plan) {
        RequestParams params = new RequestParams();
        params.add("jsonPlan", JsonTool.toJson(plan));
        mClient.post(AddUrl, params, new AsyncHttpResponseHandler() {
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
    public void AddPlanPeople(Plan plan) {
        RequestParams params = new RequestParams();
        params.add("planName",plan.getPl_name());
        mClient.post(AddPeopleUrl, params, new AsyncHttpResponseHandler() {
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
    public void AdjustPlan(Plan plan) {
        RequestParams params = new RequestParams();
        params.add("jsonPlan",JsonTool.toJson(plan));
        mClient.post(AdjustUrl, params, new AsyncHttpResponseHandler() {
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
    public void DeletePlan(Plan plan) {
        RequestParams params = new RequestParams();
        params.add("planName", plan.getPl_name());
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
    public void ListPlan() {
        mClient.get(ListUrl, new AsyncHttpResponseHandler() {
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
