package com.example.aimee.bottombar.newServer.utils.statics;


import com.example.aimee.bottombar.newServer.utils.httpUtils.basics.ActivityClient;
import com.example.aimee.bottombar.newServer.utils.httpUtils.basics.CommentClient;
import com.example.aimee.bottombar.newServer.utils.httpUtils.basics.CompanyClient;
import com.example.aimee.bottombar.newServer.utils.httpUtils.basics.KnowledgeClient;
import com.example.aimee.bottombar.newServer.utils.httpUtils.basics.PackageClient;
import com.example.aimee.bottombar.newServer.utils.httpUtils.basics.PurchaseClient;
import com.example.aimee.bottombar.newServer.utils.httpUtils.basics.TopicClient;
import com.example.aimee.bottombar.newServer.utils.httpUtils.basics.UserClient;
import com.example.aimee.bottombar.newServer.utils.httpUtils.basics.ZanClient;
import com.example.aimee.bottombar.newServer.utils.httpUtils.impls.ActivityClientImpl;
import com.example.aimee.bottombar.newServer.utils.httpUtils.impls.CommentClientImpl;
import com.example.aimee.bottombar.newServer.utils.httpUtils.impls.CompanyClientImpl;
import com.example.aimee.bottombar.newServer.utils.httpUtils.impls.KnowledgeClientImpl;
import com.example.aimee.bottombar.newServer.utils.httpUtils.impls.PackageClientImpl;
import com.example.aimee.bottombar.newServer.utils.httpUtils.impls.PurchaseClientImpl;
import com.example.aimee.bottombar.newServer.utils.httpUtils.impls.TopicClientImpl;
import com.example.aimee.bottombar.newServer.utils.httpUtils.impls.UserClientImpl;
import com.example.aimee.bottombar.newServer.utils.httpUtils.impls.ZanClientImpl;

/**
 * Created by TonyJiang on 2016/5/3.
 */
public class NewHttpFactory {
    public static ActivityClient getActivityClient(){
        return new ActivityClientImpl(Global.mHandler);
    }
    public static CommentClient getCommentClient(){
        return new CommentClientImpl(Global.mHandler);
    }
    public static CompanyClient getCompanyClient(){
        return new CompanyClientImpl(Global.mHandler);
    }
    public static KnowledgeClient getKnowledgeClient(){
        return new KnowledgeClientImpl(Global.mHandler);
    }
    public static PackageClient getPackageClient(){
        return new PackageClientImpl(Global.mHandler);
    }
    public static PurchaseClient getPurchaseClient(){
        return new PurchaseClientImpl(Global.mHandler);
    }
    public static TopicClient getTopicClient(){
        return new TopicClientImpl(Global.mHandler);
    }
    public static UserClient getUserClient(){
        return new UserClientImpl(Global.mHandler);
    }
    public static ZanClient getZanClient(){
        return new ZanClientImpl(Global.mHandler);
    }
}
