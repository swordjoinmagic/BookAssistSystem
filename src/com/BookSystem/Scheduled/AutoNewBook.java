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
 * ���ڹ��������ٵݵ���
 *
 */
@Component
public class AutoNewBook {
	/**
	 * �����ٵݵĺ��ķ�����
	 * ÿ���µĵڶ��죬��������û����ر��ע��ǩ��
	 * Ȼ����ݸ����û����ر��ע��ǩ���������û���������
	 * 
	 */
	@Scheduled(cron="0 0 12 1/1 * ?")
	public void autoBorrow() {
		// ��һ�����ҵ����п��������ٵݵ��û�
		SqlSession session = MybatisManager.getSqlsessionfactory().openSession();
		MybatisUserMapper userMapper = session.getMapper(MybatisUserMapper.class);
		
		List<User>users = userMapper.findUsersByEnableAutoNewBook(1);
		
		// �ڶ�������ÿ���û���������ע�ı�ǩ������
		
	}
}
