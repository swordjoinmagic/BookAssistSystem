package com.BookSystem.Scheduled;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.BookSystem.DataBaseManagement.MybatisManager;
import com.BookSystem.HttpUtil.HttpClientUtil;
import com.BookSystem.MailManager.EmailManager;
import com.BookSystem.MybatisMapper.MybatisUserMapper;
import com.BookSystem.javaBean.User;

/**
 * 
 * 用于管理自动续借的类
 *
 */
@Component
public class AutoBorrow {
	/**
	 * 自动续借的核心方法，
	 * 每隔一天，自动搜查所有开启自动续借的用户，
	 * 检查每一位用户的借阅书籍是否准备到期（4天以内），
	 * 到期则发送邮件通知用户。
	 */
	@Scheduled(cron="0 0 12 1/1 * ?")
	public void autoBorrow() {
		// 第一步，找到所有自动续借的用户
		SqlSession session = MybatisManager.getSqlsessionfactory().openSession();
		MybatisUserMapper userMapper = session.getMapper(MybatisUserMapper.class);
		
		List<User>users = null;
		try {
			users = userMapper.findUsersByEnableAutoBorrow(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(users);
		
		if(users!=null) {
			for(User user:users) {
				String userEmail = user.getEmail();
				
				// 请求自动续借的接口
				JSONObject jsonObject = HttpClientUtil.get("http://localhost:8088/interface/autoBorrow?userID="+user.getUserName());
				
				JSONArray success = (JSONArray) jsonObject.get("successBooks");
				JSONArray fails = (JSONArray) jsonObject.get("failBooks");
				
				System.out.println(success);
				System.out.println(fails);
				
				EmailManager.getEmailManager().sendAutoBorrowTipsEmail(userEmail, success, fails);
			}
		}
	}
}
