package com.example.aimee.bottombar.newServer.utils.statics;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Created by TonyJiang on 2016/3/26.
 */
public class Global {
    public final static String LocalHost = "http://aimeeking.xicp.net:11593/";
    //http://aimeeking.xicp.net:11593/http://aimeeking.xicp.net:33727/
    //http://10.0.2.2:8080/"http://aimeeking.xicp.net:18358/TourismServer2/";//"http://aimeeking.xicp.net:18358/TourismServer2/";//http://10.0.2.2:8080/TourismServer2/
    public static final int NLG = 233;
    public static final int TPC = 122;
    public static final int PKG = 123;
    public static final int ACT = 213;

    public static final int NotHR = 2345;
    public static final int IsHRbtFail = 1334;
    public static final int IsHRadSuccess = 3224;
    public static Handler mHandler;
    public static Handler mHandler2;
    public static Context mContext;

    public final static int SUCCESS = 0x234;
    public final static int FAIL = 0x123;
   /* public String userCode = "20160505221144000006";

    public  String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
*/
    public static Message DealFinal(byte[] bytes, int resultType){
        String result = new String(bytes);
        Bundle bundle = new Bundle();
        Message msg = new Message();
        bundle.putString("result",result);
        msg.what=resultType;
        msg.setData(bundle);
        return msg;
    }

    public static void setmContext(Context mContext) {
        Global.mContext = mContext;
    }
    public static Context getmContext() {
        return mContext;
    }
}
