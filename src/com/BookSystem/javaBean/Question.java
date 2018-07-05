package com.BookSystem.javaBean;

import java.util.List;

public class Question {
	// 问题描述
	private String description;
	// 问题里的参数
	private List<String> params;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<String> getParams() {
		return params;
	}
	public void setParams(List<String> params) {
		this.params = params;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	// 问题的答案
	private String answer;
}
