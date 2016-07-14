package com.example.aimee.bottombar.newServer.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MPackageModel implements Serializable{
	private String code;
	
	private String pkgName;

    private String refActivityCode;
    
    private String refCompanyCode;

    private Date pubTime;

    private Date activeTime;

    private BigDecimal price;

    private String title;

    private String content;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	public String getRefActivityCode() {
		return refActivityCode;
	}

	public void setRefActivityCode(String refActivityCode) {
		this.refActivityCode = refActivityCode;
	}

	public String getRefCompanyCode() {
		return refCompanyCode;
	}

	public void setRefCompanyCode(String refCompanyCode) {
		this.refCompanyCode = refCompanyCode;
	}

	public Date getPubTime() {
		return pubTime;
	}

	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}

	public Date getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	private static final long serialVersionUID = 6L; // 定义序列化ID
}
