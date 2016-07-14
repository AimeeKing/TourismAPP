package com.example.aimee.bottombar.newServer.response;

import java.util.List;

import com.example.aimee.bottombar.newServer.model.PurchaseModel;

public class PurchaseResponse extends Response {
	private List<PurchaseModel> purchaseModels;

	public List<PurchaseModel> getPurchaseModels() {
		return purchaseModels;
	}

	public void setPurchaseModels(List<PurchaseModel> purchaseModels) {
		this.purchaseModels = purchaseModels;
	}
	private String code;
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
