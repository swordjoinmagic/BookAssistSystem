package com.BookSystem.Scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 
 * ���ڹ����Զ��������
 *
 */
@Component
public class AutoBorrow {
	/**
	 * �Զ�����ĺ��ķ�����
	 * ÿ��һ�죬�Զ��Ѳ����п����Զ�������û���
	 * ���ÿһλ�û��Ľ����鼮�Ƿ�׼�����ڣ�4�����ڣ���
	 * ���������ʼ�֪ͨ�û���
	 */
	@Scheduled(cron="0 0 12 1/1 * ?")
	public void autoBorrow() {
		
	}
}
