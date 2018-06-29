package com.BookSystem.javaBean;

public class Comment {
	private int id;
	public int getId() {
		return id;
	}
	@Override
	public String toString() {
		return "Comment [id=" + id + ", comment=" + comment + ", fromISBN=" + fromISBN + ", fromUserID=" + fromUserID
				+ "]";
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getFromISBN() {
		return fromISBN;
	}
	public void setFromISBN(String fromISBN) {
		this.fromISBN = fromISBN;
	}
	public String getFromUserID() {
		return fromUserID;
	}
	public void setFromUserID(String fromUserID) {
		this.fromUserID = fromUserID;
	}
	private String comment;
	private String fromISBN;
	private String fromUserID;
	
}
