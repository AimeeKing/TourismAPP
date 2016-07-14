package com.example.aimee.bottombar.newServer.utils.httpUtils.basics;

import com.example.aimee.bottombar.newServer.request.PurchaseRequest;

/**
 * Created by TonyJiang on 2016/5/3.
 */
public interface PurchaseClient {
    public void addPurchase(PurchaseRequest request);
    public void delPurchase(PurchaseRequest request);
    public void listPurchase(PurchaseRequest request);
    public void adjustPurchase(PurchaseRequest request);
}
