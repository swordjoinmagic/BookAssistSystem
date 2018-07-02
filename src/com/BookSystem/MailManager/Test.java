package com.BookSystem.MailManager;

import javax.mail.*;

//文件名 SendEmail.java

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Test {
	
	public static void main(String[] args) {
		// 收件人电子邮箱
		String to = "16240011@mail.szpt.edu.cn";

		// 发件人电子邮箱
		String from = "16240011@mail.szpt.edu.cn";

		// 指定发送邮件的主机为 localhost
		String host = "smtp.exmail.qq.com";

		// 获取系统属性
		Properties properties = System.getProperties();

		properties.put("mail.smtp.auth", "true");
		// 设置邮件服务器
		properties.setProperty("mail.smtp.host", host);
		
		properties.setProperty("mail.user", "16240011@mail.szpt.edu.cn");
		properties.setProperty("mail.password", "Sz09043330");
		
		Authenticator authenticator = new Authenticator() {
		             @Override
		             protected PasswordAuthentication getPasswordAuthentication() {
		                 // 用户名、密码
		                 String userName = properties.getProperty("mail.user");
		                 String password = properties.getProperty("mail.password");
		                 return new PasswordAuthentication(userName, password);
		             }
		         };

		// 获取默认session对象
        Session session = Session.getInstance(properties, authenticator);
		try {
			// 创建默认的 MimeMessage 对象
			MimeMessage message = new MimeMessage(session);

			// Set From: 头部头字段
			message.setFrom(new InternetAddress(from));

			// Set To: 头部头字段
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: 头部头字段
			message.setSubject("This is the Subject Line!");

			// 设置消息体
	        message.setContent("<h1>This is actual message</h1><ol><li>a</li><li>a</li><li>a</li><li>a</li></ol>",
                     "text/html" );

			// 发送消息
			Transport.send(message);
			
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
