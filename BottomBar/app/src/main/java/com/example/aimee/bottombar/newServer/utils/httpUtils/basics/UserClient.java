package com.example.aimee.bottombar.newServer.utils.httpUtils.basics;

import com.example.aimee.bottombar.newServer.request.UserRequest;

/**
 * Created by TonyJiang on 2016/5/3.
 */
public interface UserClient {
    public void login(UserRequest request);
    public void register(UserRequest request);
    public void update(UserRequest request);
    public void search(UserRequest request);
}
