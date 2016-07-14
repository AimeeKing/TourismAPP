package com.example.aimee.bottombar.newServer.request;

import com.example.aimee.bottombar.newServer.model.PurchaseModel;

import java.util.Date;


public class PurchaseRequest extends Request{
	private String code;
	private String content;
	private int count;
	private Date purchaseDate;
	private String rateStatus;
	private String refPackageCode;
	private String refUserCode;
	private String status;
	private String userCode;

    public PurchaseRequest(PurchaseModel purchase){
        content = purchase.getContent();
        count = purchase.getCount();
        purchaseDate = purchase.getPurchaseDate();
        rateStatus = purchase.getRateStatus();
        refPackageCode = purchase.getRefPackageCode();
        refUserCode = purchase.getRefUserCode();
        status = purchase.getStatus();

    }
    public PurchaseRequest(){

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
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getRateStatus() {
		return rateStatus;
	}
	public void setRateStatus(String rateStatus) {
		this.rateStatus = rateStatus;
	}
	public String getRefPackageCode() {
		return refPackageCode;
	}
	public void setRefPackageCode(String refPackageCode) {
		this.refPackageCode = refPackageCode;
	}
	public String getRefUserCode() {
		return refUserCode;
	}
	public void setRefUserCode(String refUserCode) {
		this.refUserCode = refUserCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	protected void setParams() {
		if(code!=null)
			params.add("code",code);
		if(content!=null)
			params.add("content",content);
		if(count>0)
			params.put("count",count);
		if(purchaseDate!=null)
			params.put("purchaseDate",purchaseDate);
		if(rateStatus!=null)
			params.add("rateStatus",rateStatus);
		if(refPackageCode!=null)
			params.add("refPackageCode",refPackageCode);
		if(refUserCode!=null)
			params.add("refUserCode",refUserCode);
		if(count>0)
			params.put("count",count);
		if(status!=null)
			params.put("status",status);
	}
}
