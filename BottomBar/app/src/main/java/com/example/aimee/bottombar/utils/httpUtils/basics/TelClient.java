package com.example.aimee.bottombar.utils.httpUtils.basics;

import com.example.aimee.bottombar.model.Tel;
import com.example.aimee.bottombar.model.User;

/**
 * Created by TonyJiang on 2016/3/26.
 */
public interface TelClient {
    public void AddTel(Tel tel);
    public void DeleteTel(Tel tel);
    public void ListTel(User user);
}
