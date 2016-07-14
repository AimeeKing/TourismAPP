package com.example.aimee.bottombar.newServer.model;

import java.io.Serializable;
import java.util.Date;


public class MActivityModel implements Serializable {
	private String activityName;

    private String title;

    private String shortDesc;

    private String publisherCode;

    private String ageRange;

    private Integer ageFlag;

    private Date pubTime;

    private String destination;

    private Integer destType;

    private Integer count;

    private String image;

    private Integer type;

    private String content;
    
    private String code;

	private static final long serialVersionUID = 2L; // 定义序列化ID

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	public String getPublisherCode() {
		return publisherCode;
	}

	public void setPublisherCode(String publisherCode) {
		this.publisherCode = publisherCode;
	}

	public String getAgeRange() {
		return ageRange;
	}

	public void setAgeRange(String ageRange) {
		this.ageRange = ageRange;
	}

	public Integer getAgeFlag() {
		return ageFlag;
	}

	public void setAgeFlag(Integer ageFlag) {
		this.ageFlag = ageFlag;
	}

	public Date getPubTime() {
		return pubTime;
	}

	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Integer getDestType() {
		return destType;
	}

	public void setDestType(Integer destType) {
		this.destType = destType;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
    
    
}
