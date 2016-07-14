package com.example.aimee.bottombar.newServer.request;

import com.example.aimee.bottombar.newServer.model.CommentModel;

import java.util.Date;


public class CommentRequest extends Request{
	public CommentRequest(CommentModel comment){
		replyTo = comment.getReplyTo();
		publisherCode = comment.getPublisherCode();
		pubTime = comment.getPubTime();
		content = comment.getContent();
		type = comment.getType();
		typeFlag = comment.getTypeFlag();
		refCode = comment.getRefCode();
	}
	public CommentRequest(){

	}
	public String code;
	public String replyTo;
	public String publisherCode;
	public Date pubTime;
	public String content;
	public String type;
	public int typeFlag;
	public String refCode;
	public String userCode;
	
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getReplyTo() {
		return replyTo;
	}
	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPublisherCode() {
		return publisherCode;
	}
	public void setPublisherCode(String publisherCode) {
		this.publisherCode = publisherCode;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getTypeFlag() {
		return typeFlag;
	}
	public void setTypeFlag(int typeFlag) {
		this.typeFlag = typeFlag;
	}
	public String getRefCode() {
		return refCode;
	}
	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}
	public Date getPubTime() {
		return pubTime;
	}
	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}


	@Override
	protected void setParams() {
		if(code!=null)
			params.add("code",code);
		if(replyTo!=null)
			params.add("replyTo",replyTo);
		if(publisherCode!=null)
			params.add("publisherCode",publisherCode);
		if(pubTime!=null)
			params.put("pubTime",pubTime);
		if(content!=null)
			params.add("content",content);
		if(type!=null)
			params.add("type",type);
		if(typeFlag>0)
			params.put("typeFlag",typeFlag);
		if(refCode!=null)
			params.add("refCode",refCode);
		if(userCode!=null)
			params.add("userCode",userCode);
	}

}
