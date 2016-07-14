package com.example.aimee.bottombar.newServer.request;

import com.loopj.android.http.RequestParams;

public abstract class Request{
	private String appId;
	protected RequestParams params;
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	protected abstract void setParams();

	public RequestParams getParams(){
        params=new RequestParams();
		setParams();
		return params;
	}
	
}
