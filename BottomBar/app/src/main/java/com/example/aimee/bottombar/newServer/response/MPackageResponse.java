package com.example.aimee.bottombar.newServer.response;

import java.util.List;

import com.example.aimee.bottombar.newServer.model.MPackageModel;

public class MPackageResponse extends Response {
	
	List<MPackageModel> packageModels;

	public List<MPackageModel> getPackageModels() {
		return packageModels;
	}

	public void setPackageModels(List<MPackageModel> packageModels) {
		this.packageModels = packageModels;
	}

	private String code;
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
    
    
}
