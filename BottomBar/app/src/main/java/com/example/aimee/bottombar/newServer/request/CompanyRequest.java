package com.example.aimee.bottombar.newServer.request;

import com.example.aimee.bottombar.newServer.model.Company;

import java.util.Date;


public class CompanyRequest extends Request{
	private String companyName;
	private String content;
	private Date joinTime;
	private String code;
	private String publisherCode;
	private String userCode;

	public CompanyRequest(Company company){
		companyName = company.getCompanyName();
		content = company.getContent();
		joinTime = company.getJoinTime();
        publisherCode = company.getPublisherCode();
	}
    public CompanyRequest(){

    }
	
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getPublisherCode() {
		return publisherCode;
	}
	public void setPublisherCode(String publisherCode) {
		this.publisherCode = publisherCode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getJoinTime() {
		return joinTime;
	}
	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}

	@Override
	protected void setParams() {
		if(userCode!=null)
			params.add("userCode",userCode);
		if(publisherCode!=null)
			params.add("publisherCode",publisherCode);
		if(code!=null)
			params.add("code",code);
		if(companyName!=null)
			params.add("companyName",companyName);
		if(content!=null)
			params.add("content",content);
		if(joinTime!=null)
			params.put("joinTime",joinTime);
		if(getAppId()!=null)
			params.put("appId",getAppId());
	}
}
