package com.example.aimee.bottombar.tony.utils.httpUtils.basics;

/**
 * Created by Aimee on 2016/4/5.
 */
public interface ActClient {
    public void addAct(String actJson);
    public void searchAct(String name,String value);
    public void getAct();
}
