package com.example.aimee.bottombar.newServer.utils.httpUtils.basics;

import com.example.aimee.bottombar.newServer.request.ZanRequest;

/**
 * Created by TonyJiang on 2016/5/3.
 */
public interface ZanClient {
    public void addZan(ZanRequest request);
    public void delZan(ZanRequest request);
    public void getCount(ZanRequest request);
    public void listZan(ZanRequest request);
}
