package com.example.aimee.bottombar.newServer.request;

import com.example.aimee.bottombar.newServer.model.KnowledgeModel;

import java.util.Date;


//public class KnowledgeRequest extends Request{
public class KnowledgeRequest extends Request{
	private String nlgName;
	private String content;
	private Date pubTime;
	private String code;
	private String userCode;
	private String publisherCode;
	
	public KnowledgeRequest(KnowledgeModel nlg){
		nlgName = nlg.getNlgName();
		content = nlg.getContent();
		pubTime = nlg.getPubTime();
		publisherCode  = nlg.getPublisherCode();
	}
	public KnowledgeRequest(){

	}
	public String getPublisherCode() {
		return publisherCode;
	}
	public void setPublisherCode(String publisherCode) {
		this.publisherCode = publisherCode;
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
	public String getNlgName() {
		return nlgName;
	}
	public void setNlgName(String nlgName) {
		this.nlgName = nlgName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getPubTime() {
		return pubTime;
	}
	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}

	@Override
	protected void setParams() {
		if(userCode!=null){
			params.add("userCode",userCode);
		}
        if(publisherCode!=null)
            params.add("publisherCode",publisherCode);
        if(code!=null)
            params.add("code",code);
        if(nlgName!=null)
            params.add("nlgName",nlgName);
        if(content!=null)
            params.add("content",content);
        if(pubTime!=null)
            params.put("pubTime",pubTime);
	}
}
