package com.example.aimee.bottombar.newServer.utils.httpUtils.basics;

import com.example.aimee.bottombar.newServer.request.CommentRequest;

/**
 * Created by TonyJiang on 2016/5/3.
 */
public interface CommentClient {
    public void addComment(CommentRequest request);
    public void delComment(CommentRequest request);
    public void listComment(CommentRequest request);
}
