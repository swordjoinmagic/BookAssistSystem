package com.BookSystem.MybatisMapper;

import com.BookSystem.javaBean.Question;

public interface MybatisQuestionMapper {
	/**
	 * 从数据库里面随机拿一条问题
	 * @return
	 */
	public Question getRandomQuestion();
}
