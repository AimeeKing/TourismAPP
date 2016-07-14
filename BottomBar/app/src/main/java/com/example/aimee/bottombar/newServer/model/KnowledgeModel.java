package com.example.aimee.bottombar.newServer.model;

import java.io.Serializable;
import java.util.Date;

public class KnowledgeModel implements Serializable{
	private int imageResourse;
	private String nlgName;
	private Date pubTime;
	private String content;
	private String code;
	private String publisherCode;
	private String image;
    private static final long serialVersionUID = 1L; // 定义序列化ID

	public int getImageResourse() {
		return imageResourse;
	}

	public void setImageResourse(int imageResourse) {
		this.imageResourse = imageResourse;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImage() {
		return image;
	}

	public String getPublisherCode() {
		return publisherCode;
	}
	public void setPublisherCode(String publisherCode) {
		this.publisherCode = publisherCode;
	}
	public String getNlgName() {
		return nlgName;
	}
	public void setNlgName(String nlgName) {
		this.nlgName = nlgName;
	}
	
	
	public Date getPubTime() {
		return pubTime;
	}
	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
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
	
}
