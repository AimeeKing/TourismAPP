package com.example.aimee.bottombar.newServer.response;

import com.example.aimee.bottombar.newServer.model.MActivityModel;

import java.util.List;

public class MActivityResponse extends Response  {
	private List<MActivityModel> activityModels;

	public List<MActivityModel> getActivityModels() {
		return activityModels;
	}

	public void setActivityModels(List<MActivityModel> activityModels) {
		this.activityModels = activityModels;
	}
	private String code;
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
