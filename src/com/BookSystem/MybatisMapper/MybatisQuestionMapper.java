package com.BookSystem.MybatisMapper;

import com.BookSystem.javaBean.Question;

public interface MybatisQuestionMapper {
	/**
	 * �����ݿ����������һ������
	 * @return
	 */
	public Question getRandomQuestion();
}
