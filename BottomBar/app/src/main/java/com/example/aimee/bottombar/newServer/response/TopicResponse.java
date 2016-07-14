package com.example.aimee.bottombar.newServer.response;

import java.util.List;


import com.example.aimee.bottombar.newServer.model.TopicModel;

public class TopicResponse extends Response {
	private List<TopicModel> topicModels;

	public List<TopicModel> getTopicModels() {
		return topicModels;
	}

	public void setTopicModels(List<TopicModel> topicModels) {
		this.topicModels = topicModels;
	}
	private String code;
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
