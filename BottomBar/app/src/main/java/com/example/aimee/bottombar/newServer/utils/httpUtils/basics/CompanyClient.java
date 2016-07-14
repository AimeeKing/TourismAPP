package com.example.aimee.bottombar.newServer.utils.httpUtils.basics;

import com.example.aimee.bottombar.newServer.request.CompanyRequest;

/**
 * Created by TonyJiang on 2016/5/3.
 */
public interface CompanyClient {
    public void addCompany(CompanyRequest request);
    public void adjustCompany(CompanyRequest request);
}
