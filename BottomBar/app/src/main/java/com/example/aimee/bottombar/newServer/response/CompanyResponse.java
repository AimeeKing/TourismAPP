package com.example.aimee.bottombar.newServer.response;

import com.example.aimee.bottombar.newServer.model.Company;

import java.util.Date;


public class CompanyResponse extends Response {
	private String code;
	private String companyName;
	private String content;
	private Date joinTime;
	private String publisherCode;
	
	public Company getCompany(){
		Company company = new Company();
		company.setCompanyName(companyName);
		company.setContent(content);
		company.setJoinTime(joinTime);
		company.setPublisherCode(publisherCode);
		company.setCode(code);
		return company;
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
	
}
