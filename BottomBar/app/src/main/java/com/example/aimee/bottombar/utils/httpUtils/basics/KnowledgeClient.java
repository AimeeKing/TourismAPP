package com.example.aimee.bottombar.utils.httpUtils.basics;

import com.example.aimee.bottombar.model.Knowledge;

/**
 * Created by TonyJiang on 2016/3/26.
 */
public interface KnowledgeClient {
    public void AddNLG(Knowledge nlg);
    public void DeleteNLG(Knowledge nlg);
    public void AdjustNLG(Knowledge nlg);
    public void ListNLG();
}
