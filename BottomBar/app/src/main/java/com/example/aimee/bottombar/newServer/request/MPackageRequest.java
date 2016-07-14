package com.example.aimee.bottombar.newServer.request;

import com.example.aimee.bottombar.newServer.model.MPackageModel;

import java.math.BigDecimal;
import java.util.Date;


public class MPackageRequest extends Request{
	private String userCode;
	
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	private String code;
	
	private String pkgName;

    private String refActivityCode;
    
    private String refCompanyCode;

    private Date pubTime;

    private Date activeTime;

    private BigDecimal price;

    private String title;

    private String content;

	public MPackageRequest(MPackageModel pkg){
		pkgName = pkg.getPkgName();
		content = pkg.getContent();
		refActivityCode = pkg.getRefActivityCode();
		refCompanyCode = pkg.getRefCompanyCode();
		pubTime = pkg.getPubTime();
		activeTime = pkg.getActiveTime();
		price = pkg.getPrice();
		title = pkg.getTitle();
	}

	public MPackageRequest(){

	}
	public String getCode() {
		return code;
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

	public void setCode(String code) {
		this.code = code;
	}


	@Override
	protected void setParams() {
		if(code!=null)
			params.add("code",code);
		if(content!=null)
			params.add("content",content);
		if(title!=null)
			params.add("title",title);
		if(activeTime!=null)
			params.put("activeTime",activeTime);
		if(pkgName!=null)
			params.put("pkgName",pkgName);
		if(price!=null)
			params.put("price",price);
		if(pubTime!=null)
			params.put("pubTime",pubTime);
		if(refActivityCode!=null)
			params.put("refActivityCode",refActivityCode);
		if(refCompanyCode!=null)
			params.put("refCompanyCode",refCompanyCode);
		if(userCode!=null)
			params.add("userCode",userCode);
	}
}
