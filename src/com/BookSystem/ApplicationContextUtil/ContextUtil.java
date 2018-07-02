package com.BookSystem.ApplicationContextUtil;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 
 * ���ڻ�ȡApplicationContext�ĵ�����
 *
 */
public class ContextUtil {
	private static ApplicationContext context;
	
	/**
	 * ʹ��˫�ؼ��������õ�������
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		if(context==null) {
			synchronized(ContextUtil.class) {
				if(context==null) {
					WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
					ServletContext servletContext = webApplicationContext.getServletContext();
					context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
				}
			}
		}
		return context;
	}
}
