package com.example.aimee.bottombar.utils.statics.Factories;

import android.os.Handler;

import com.example.aimee.bottombar.utils.httpUtils.basics.CommentClient;
import com.example.aimee.bottombar.utils.httpUtils.basics.KnowledgeClient;
import com.example.aimee.bottombar.utils.httpUtils.basics.PlanClient;
import com.example.aimee.bottombar.utils.httpUtils.basics.TelClient;
import com.example.aimee.bottombar.utils.httpUtils.basics.TopicClient;
import com.example.aimee.bottombar.utils.httpUtils.basics.UserClient;
import com.example.aimee.bottombar.utils.httpUtils.impls.CommentClientImpl;
import com.example.aimee.bottombar.utils.httpUtils.impls.KnowledgeClientImpl;
import com.example.aimee.bottombar.utils.httpUtils.impls.PlanClientImpl;
import com.example.aimee.bottombar.utils.httpUtils.impls.TelClientImpl;
import com.example.aimee.bottombar.utils.httpUtils.impls.TopicClientImpl;
import com.example.aimee.bottombar.utils.httpUtils.impls.UserClientImpl;

/**
 * Created by TonyJiang on 2016/3/26.
 */
public class HttpFactory {
    public static CommentClient getCommentClient(Handler handler){
        return new CommentClientImpl(handler);
    }

    public static KnowledgeClient getKnowledgeClient(Handler handler){
        return new KnowledgeClientImpl(handler);
    }

    public static PlanClient getPlanClient(Handler handler){
        return new PlanClientImpl(handler);
    }

    public static TelClient getTelClient(Handler handler){
        return new TelClientImpl(handler);
    }

    public static TopicClient getTopicClient(Handler handler){
        return new TopicClientImpl(handler);
    }

    public static UserClient getUserClient(Handler handler){
        return new UserClientImpl(handler);
    }
}

