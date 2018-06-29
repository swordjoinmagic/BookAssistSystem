package com.BookSystem.Scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
		
	}
}
