package com.example.aimee.bottombar.newServer.utils.httpUtils.basics;

import com.example.aimee.bottombar.newServer.request.MActivityRequest;

/**
 * Created by TonyJiang on 2016/5/3.
 */
public interface ActivityClient {
    public void addActivity(MActivityRequest request);
    public void delActivity(MActivityRequest request);
    public void listActivity();
    public void listActivity(MActivityRequest request);
    public void adjustActivity(MActivityRequest request);
    public void searchActivity(MActivityRequest request);
}
