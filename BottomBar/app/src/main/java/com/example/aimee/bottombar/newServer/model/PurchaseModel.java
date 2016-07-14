package com.example.aimee.bottombar.newServer.model;

import java.io.Serializable;
import java.util.Date;

public class PurchaseModel implements Serializable{
	private String code;
	
	private String content;
	
	private String refUserCode;

    private String refPackageCode;

    private Integer count;

    private Date purchaseDate;

    private String status;

    private String rateStatus;

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

	public String getRefUserCode() {
		return refUserCode;
	}

	public void setRefUserCode(String refUserCode) {
		this.refUserCode = refUserCode;
	}

	public String getRefPackageCode() {
		return refPackageCode;
	}

	public void setRefPackageCode(String refPackageCode) {
		this.refPackageCode = refPackageCode;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRateStatus() {
		return rateStatus;
	}

	public void setRateStatus(String rateStatus) {
		this.rateStatus = rateStatus;
	}

	private static final long serialVersionUID = 5L; // 定义序列化ID
}
