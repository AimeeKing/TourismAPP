package com.example.aimee.bottombar.newServer.response;

import com.example.aimee.bottombar.newServer.model.User;

import java.util.Date;


public class UserResponse extends Response {
	private String code;
	private String logoImgUrl;
	private Date lastLogin;
	private String name;
	private String nickName;
	private String phone;
	private String sex;
	private String userName;
	private User user;

	public User getUser(){
		user = new User();
		user.setUserName(userName);
		user.setImage(logoImgUrl);
		user.setLastLogin(lastLogin);
		user.setName(name);
		user.setNickName(nickName);
		user.setSex(sex);
		user.setCode(code);
        return user;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLogoImgUrl() {
		return logoImgUrl;
	}
	public void setLogoImgUrl(String logoImgUrl) {
		this.logoImgUrl = logoImgUrl;
	}
	
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
