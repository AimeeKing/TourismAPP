package com.example.aimee.bottombar.tony.utils.httpUtils.impls;

import android.os.Handler;

import com.example.aimee.bottombar.tony.utils.httpUtils.basics.PkgClient;
import com.example.aimee.bottombar.tony.utils.statics.Global;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Aimee on 2016/4/10.
 */
public class PkgClientImpl implements PkgClient {
    protected String getUrl= Global.LocalHost+"/ListPackages";
    private Handler mhandler;
    private AsyncHttpClient myActClient;

    public PkgClientImpl(Handler mhandler) {
        this.mhandler = mhandler;
        myActClient = new AsyncHttpClient();
        myActClient.setTimeout(5000);
        myActClient.setConnectTimeout(5000);
    }

    @Override
    public void getPkg(String activity_id) {
        RequestParams params = new RequestParams();
        params.add("activity_id", activity_id);
        myActClient.post(getUrl, params, new AsyncHttpResponseHandler() {
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
