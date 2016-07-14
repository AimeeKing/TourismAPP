package com.example.aimee.bottombar.newServer.request;

import com.example.aimee.bottombar.newServer.model.TopicModel;

import java.io.Serializable;
import java.util.Date;


public class TopicRequest extends Request implements Serializable {
	private String code;
	private String content;
	private String image;
	private String publisherCode;
	private String title;
	private Date pubTime;
	private int peopleNum;
	private String userCode;

	public TopicRequest(TopicModel topic){
		content = topic.getContent();
		image = topic.getImage();
		publisherCode = topic.getPublisherCode();
		title = topic.getTitle();
		pubTime = topic.getPubTime();
		peopleNum = topic.getPeopleNum();
	}
	public TopicRequest(){

	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getPublisherCode() {
		return publisherCode;
	}
	public void setPublisherCode(String publisherCode) {
		this.publisherCode = publisherCode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getPubTime() {
		return pubTime;
	}
	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}
	public int getPeopleNum() {
		return peopleNum;
	}
	public void setPeopleNum(int peopleNum) {
		this.peopleNum = peopleNum;
	}


	@Override
	protected void setParams() {
		if(code!=null)
			params.put("code",code);
		if(content!=null)
			params.add("content",content);
		if(image!=null)
			params.add("image",image);
		if(peopleNum>0)
			params.put("peopleNum",peopleNum);
		if(publisherCode!=null)
			params.add("publisherCode",publisherCode);
		if(pubTime!=null)
			params.put("pubTime",pubTime);
		if(title!=null)
			params.put("title",title);
		if(userCode!=null)
			params.add("userCode",userCode);
	}
}
