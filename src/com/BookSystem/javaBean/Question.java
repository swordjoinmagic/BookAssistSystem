package com.BookSystem.javaBean;

import java.util.List;

public class Question {
	// ��������
	private String description;
	// ������Ĳ���
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
	// ����Ĵ�
	private String answer;
}
