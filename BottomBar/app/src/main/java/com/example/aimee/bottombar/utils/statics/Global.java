package com.example.aimee.bottombar.utils.statics;

import android.os.Bundle;
import android.os.Message;

/**
 * Created by TonyJiang on 2016/3/26.
 */
public class Global {
    public final static String LocalHost = "http://10.0.2.2:8080/TourismServer/";
    public final static int NLG = 233;
    public final static int TPC = 122;
    public final static int SUCCESS = 0x234;
    public final static int FAIL = 0x123;

    public static Message DealFinal(byte[] bytes,int resultType){
        String result = new String(bytes);
        Bundle bundle = new Bundle();
        Message msg = new Message();
        bundle.putString("result",result);
        msg.what=resultType;
        msg.setData(bundle);
        return msg;
    }
}
