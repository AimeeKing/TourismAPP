package com.example.aimee.bottombar.newServer.response;

import java.util.List;

import com.example.aimee.bottombar.newServer.model.CommentModel;

public class CommentResponse extends Response {
	private String code;
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private List<CommentModel> commentList;

	public List<CommentModel> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<CommentModel> commentList) {
		this.commentList = commentList;
	}
	
}
