package com.BookSystem.Scheduled;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.BookSystem.DataBaseManagement.MybatisManager;
import com.BookSystem.HttpUtil.HttpClientUtil;
import com.BookSystem.MybatisMapper.MybatisUserMapper;
import com.BookSystem.javaBean.User;

/**
 * 
 * 用于管理新书速递的类
 *
 */
@Component
public class AutoNewBook {
	/**
	 * 新书速递的核心方法，
	 * 每个月的第二天，检查所有用户的特别关注标签，
	 * 然后根据该名用户的特别关注标签，给该名用户推送新书
	 * 
	 */
	@Scheduled(cron="0 0 12 1/1 * ?")
	public void autoBorrow() {
		// 第一步，找到所有开启新书速递的用户
		SqlSession session = MybatisManager.getSqlsessionfactory().openSession();
		MybatisUserMapper userMapper = session.getMapper(MybatisUserMapper.class);
		
		List<User>users = userMapper.findUsersByEnableAutoNewBook(1);
		
		// 第二步，给每名用户推送他关注的标签的内容
		
	}
}
