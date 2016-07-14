package com.example.aimee.bottombar.newServer.response;

import java.util.List;

import com.example.aimee.bottombar.newServer.model.ZanModel;

public class ZanResponse extends Response{
	
	private int count;
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	private List<ZanModel> zanModels;

	public List<ZanModel> getZanModels() {
		return zanModels;
	}

	public void setZanModels(List<ZanModel> zanModels) {
		this.zanModels = zanModels;
	}
	private String code;
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
