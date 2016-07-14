package com.example.aimee.bottombar.newServer.model;

import java.io.Serializable;
import java.util.Date;

public class ZanModel implements Serializable{
	private String code;
	private int objTypeFlag;
	private Date pubTime;
	private String refUserCode;
	private String refObjectCode;

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

	public static final Long serialVersionUID = 7L;
}
