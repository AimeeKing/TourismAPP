package com.example.aimee.bottombar.tony.utils.statics.Factories;

import android.os.Handler;

import com.example.aimee.bottombar.tony.utils.HttpResult;
import com.example.aimee.bottombar.tony.utils.httpUtils.basics.ActClient;
import com.example.aimee.bottombar.tony.utils.httpUtils.basics.TopicClient;
import com.example.aimee.bottombar.tony.utils.httpUtils.basics.UserClient;
import com.example.aimee.bottombar.tony.utils.httpUtils.impls.ActClientImpl;
import com.example.aimee.bottombar.tony.utils.httpUtils.impls.ComClientImpl;
import com.example.aimee.bottombar.tony.utils.httpUtils.impls.PkgClientImpl;
import com.example.aimee.bottombar.tony.utils.httpUtils.impls.TopicClientImpl;
import com.example.aimee.bottombar.tony.utils.httpUtils.impls.UserClientImpl;
import com.example.aimee.bottombar.tony.utils.statics.Global;
import com.example.aimee.bottombar.tony.utils.statics.GsonUtil;

/**
 * Created by TonyJiang on 2016/3/26.
 */
public class HttpFactory {

    public static UserClient getUserClient(Handler handler){
        return new UserClientImpl(handler);
    }

    public static int CheckResult(String string){
        HttpResult httpResult = (HttpResult) GsonUtil.Json2Java(string, HttpResult.class);
        if(httpResult.getResult()!=null){
            if(httpResult.getStatus()==202){
                return Global.IsHRbtFail;
            }else if(httpResult.getStatus()==200){
                return Global.IsHRadSuccess;
            }
        }
        return Global.NotHR;
    }


    public static ActClient getActClient(Handler handler){
        return new ActClientImpl(handler);
    }

    public static TopicClient getTopicClient(Handler handler){return new TopicClientImpl(handler);}
    public static PkgClientImpl getPkgClient(Handler handler){return new PkgClientImpl(handler);}
    public static ComClientImpl getComClient(Handler handler){return new ComClientImpl(handler);}
}

