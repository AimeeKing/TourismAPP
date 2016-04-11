package com.example.aimee.bottombar.tony.utils.httpUtils.basics;

import com.loopj.android.http.RequestParams;

/**
 * Created by Aimee on 2016/4/10.
 */
public interface ComClient {
    public void getComment(String activity_id);
    public void addComment(RequestParams params);
}
