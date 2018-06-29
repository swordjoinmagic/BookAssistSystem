package com.BookSystem.javaBean;

public class SpecialKey {
	private int id;
	private String specialKey;
	private String keyType;
	private String fromUserID;
	@Override
	public String toString() {
		return "SpecialKey [id=" + id + ", specialKey=" + specialKey + ", keyType=" + keyType + ", fromUserID="
				+ fromUserID + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSpecialKey() {
		return specialKey;
	}
	public void setSpecialKey(String specialKey) {
		this.specialKey = specialKey;
	}
	public String getKeyType() {
		return keyType;
	}
	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}
	public String getFromUserID() {
		return fromUserID;
	}
	public void setFromUserID(String fromUserID) {
		this.fromUserID = fromUserID;
	}
}
