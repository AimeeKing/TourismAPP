package com.example.aimee.bottombar.newServer.utils.httpUtils.basics;

import com.example.aimee.bottombar.newServer.request.TopicRequest;

/**
 * Created by TonyJiang on 2016/5/3.
 */
public interface TopicClient {
    public void addTopic(TopicRequest request);
    public void delTopic(TopicRequest request);
    public void listTopic(TopicRequest request);
    public void adjustTopic(TopicRequest request);
}
