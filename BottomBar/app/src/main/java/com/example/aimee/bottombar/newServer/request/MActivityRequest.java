package com.example.aimee.bottombar.newServer.request;

import com.example.aimee.bottombar.newServer.model.MActivityModel;

import java.util.Date;


public class MActivityRequest extends Request{
	private String context;
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	private String userCode;
	private String code;
	private String activityName;
	private String ageRange;
	private int ageFlag;
	private String content;
	private String destination;
	private int count;
	private int destType;
	private String image;
	private String publisherCode;
	private Date pubTime;
	private String shortDesc;
	private String title;

	public MActivityRequest(MActivityModel activity){
		activityName = activity.getActivityName();
		ageRange = activity.getAgeRange();
		ageFlag = activity.getAgeFlag();
		content = activity.getContent();
		destination = activity.getDestination();
		count = activity.getCount();
		destType = activity.getDestType();
		image = activity.getImage();
		publisherCode = activity.getPublisherCode();
		pubTime = activity.getPubTime();
		shortDesc = activity.getShortDesc();
		title = activity.getTitle();
		type = activity.getType();
	}

	public MActivityRequest(){

	}

	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	private int type;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public int getAgeFlag() {
		return ageFlag;
	}

	public void setAgeFlag(int ageFlag) {
		this.ageFlag = ageFlag;
	}

	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getAgeRange() {
		return ageRange;
	}
	public void setAgeRange(String ageRange) {
		this.ageRange = ageRange;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getDestType() {
		return destType;
	}
	public void setDestType(int destType) {
		this.destType = destType;
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
	public Date getPubTime() {
		return pubTime;
	}
	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}
	public String getShortDesc() {
		return shortDesc;
	}
	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}


	@Override
	protected void setParams() {
		if(code!=null)
			params.add("code",code);
		if(userCode!=null)
			params.add("userCode",userCode);
		if(activityName!=null)
			params.add("activityName",activityName);
		if(context!=null)
			params.add("context",context);
		if(content!=null)
			params.add("content",content);
		if(ageRange!=null)
			params.add("ageRange",ageRange);
		if(ageFlag>0)
			params.put("ageFlag",ageFlag);
		if(destination!=null)
			params.add("destination",destination);
		if(destType>0)
			params.put("destType",destType);
		if(count>0)
			params.put("count",count);
		if(publisherCode!=null)
			params.put("publisherCode",publisherCode);
		if(pubTime!=null)
			params.put("pubTime",pubTime);
		if(getAppId()!=null)
			params.put("appId",getAppId());
		if(image!=null)
			params.put("image",image);
		if(title!=null)
			params.put("title",title);
		if(shortDesc!=null)
			params.put("shortDesc",shortDesc);
		if(type>0)
			params.put("type",type);
	}
}
