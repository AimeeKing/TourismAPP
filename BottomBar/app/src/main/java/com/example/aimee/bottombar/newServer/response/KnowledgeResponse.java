package com.example.aimee.bottombar.newServer.response;

import java.util.List;

import com.example.aimee.bottombar.newServer.model.KnowledgeModel;

public class KnowledgeResponse extends Response {
	public List<KnowledgeModel> nlgList;

	public List<KnowledgeModel> getNlgList() {
		return nlgList;
	}

	public void setNlgList(List<KnowledgeModel> nlgList) {
		this.nlgList = nlgList;
	}
	private String code;
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
