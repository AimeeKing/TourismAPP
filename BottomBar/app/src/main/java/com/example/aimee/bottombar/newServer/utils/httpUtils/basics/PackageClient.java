package com.example.aimee.bottombar.newServer.utils.httpUtils.basics;

import com.example.aimee.bottombar.newServer.request.MPackageRequest;

/**
 * Created by TonyJiang on 2016/5/3.
 */
public interface PackageClient {
    public void addPackage(MPackageRequest request);
    public void delPackage(MPackageRequest request);
    public void listPackage(MPackageRequest request);
    public void adjustPackage(MPackageRequest request);
}
