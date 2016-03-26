package com.example.aimee.bottombar.utils.httpUtils.basics;
import com.example.aimee.bottombar.model.Topic;

/**
 * Created by TonyJiang on 2016/3/26.
 */
public interface TopicClient {
    public void AddTopic(Topic topic);
    public void DeleteTopic(Topic topic);
    public void ListTopic();
    public void AddPeople(Topic topic);
}
