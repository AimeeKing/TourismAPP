package com.example.aimee.bottombar.newServer.request;

import com.example.aimee.bottombar.newServer.model.ZanModel;

import java.util.Date;


public class ZanRequest extends Request{
	private String code;
	private int objTypeFlag;
	private Date pubTime;
	private String refUserCode;
	private String refObjectCode;
	private String userCode;

    public ZanRequest(ZanModel zan){
        objTypeFlag = zan.getObjTypeFlag();
        pubTime = zan.getPubTime();
        refUserCode = zan.getRefUserCode();
        refObjectCode = zan.getRefObjectCode();
    }
    public ZanRequest(){

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
	public int getObjTypeFlag() {
		return objTypeFlag;
	}
	public void setObjTypeFlag(int objTypeFlag) {
		this.objTypeFlag = objTypeFlag;
	}
	public Date getPubTime() {
		return pubTime;
	}
	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}
	public String getRefUserCode() {
		return refUserCode;
	}
	public void setRefUserCode(String refUserCode) {
		this.refUserCode = refUserCode;
	}
	public String getRefObjectCode() {
		return refObjectCode;
	}
	public void setRefObjectCode(String refObjectCode) {
		this.refObjectCode = refObjectCode;
	}


	@Override
	protected void setParams() {
		if(code!=null)
			params.add("code",code);
		if(objTypeFlag>0)
			params.put("objTypeFlag",objTypeFlag);
		if(pubTime!=null)
			params.put("pubTime",pubTime);
		if(refObjectCode!=null)
			params.add("refObjectCode",refObjectCode);
		if(refUserCode!=null)
			params.add("refUserCode",refUserCode);
		if(userCode!=null)
			params.add("userCode",userCode);
	}
}
