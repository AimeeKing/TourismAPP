package com.example.aimee.bottombar.newServer.request;


import com.example.aimee.bottombar.newServer.model.User;

public class UserRequest extends Request{

	private String userCode;
	private String logoImgUrl;
	private String password;
	private String sex;
	private String name;
	private String userName;
	private String phone;
	private String nickName;
	private String repassword;

	public UserRequest(User user){
		logoImgUrl = user.getImage();
		password = user.getPassword();
		sex = user.getSex();
		name = user.getName();
		userName = user.getUserName();
		phone = user.getPhone();
		nickName = user.getNickName();

	}
	public UserRequest(){

	}
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getLogoImgUrl() {
		return logoImgUrl;
	}
	public void setLogoImgUrl(String logoImgUrl) {
		this.logoImgUrl = logoImgUrl;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


    @Override
    protected void setParams() {
		if(password!=null)
			params.add("password",password);
        if(userCode!=null)
            params.add("userCode",userCode);
        if(userName!=null)
            params.add("userName",userName);
        if(logoImgUrl!=null)
            params.add("logoImgUrl",logoImgUrl);
        if(getAppId()!=null)
            params.add("appId",getAppId());
        if(sex!=null)
            params.add("sex",sex);
        if(name!=null)
            params.add("name",name);
        if(phone!=null)
            params.add("phone",phone);
        if(nickName!=null)
            params.add("nickName",nickName);
        if(repassword!=null)
            params.add("repassword",repassword);
    }

}
