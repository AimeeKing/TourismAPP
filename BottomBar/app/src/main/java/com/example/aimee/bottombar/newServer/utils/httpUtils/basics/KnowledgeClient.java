package com.example.aimee.bottombar.newServer.utils.httpUtils.basics;

import com.example.aimee.bottombar.newServer.request.KnowledgeRequest;

/**
 * Created by TonyJiang on 2016/5/3.
 */
public interface KnowledgeClient {
    public void addKnowledge(KnowledgeRequest request);
    public void delKnowledge(KnowledgeRequest request);
    public void listKnowledge(KnowledgeRequest request);
    public void adjustKnowledge(KnowledgeRequest request);
}
