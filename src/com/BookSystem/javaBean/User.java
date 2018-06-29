package com.BookSystem.javaBean;

public class User {
	private String userName;
	private String password;
	private String email;
	private String nickName;
	private int isEnableAutoBorrow;
	private int isEnableAutoNewBook;
	@Override
	public String toString() {
		return "User [userName=" + userName + ", password=" + password + ", email=" + email + ", nickName=" + nickName
				+ ", isEnableAutoBorrow=" + isEnableAutoBorrow + ", isEnableAutoNewBook=" + isEnableAutoNewBook + "]";
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public int getIsEnableAutoBorrow() {
		return isEnableAutoBorrow;
	}
	public void setIsEnableAutoBorrow(int isEnableAutoBorrow) {
		this.isEnableAutoBorrow = isEnableAutoBorrow;
	}
	public int getIsEnableAutoNewBook() {
		return isEnableAutoNewBook;
	}
	public void setIsEnableAutoNewBook(int isEnableAutoNewBook) {
		this.isEnableAutoNewBook = isEnableAutoNewBook;
	} 
}
