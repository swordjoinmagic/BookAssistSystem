package com.BookSystem.MailManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.CharBuffer;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.BookSystem.ApplicationContextUtil.ContextUtil;
import com.mysql.fabric.xmlrpc.base.Array;

public class EmailManager {
	
	// 发件人电子邮箱
	private String from = "16240011@mail.szpt.edu.cn";

	// 指定发送邮件的主机为 localhost
	private String host = "smtp.exmail.qq.com";
	
	// 服务器配置的一些配置
	private Properties properties;
	private Authenticator authenticator;
	private Session session;
	
	// HTML文本
	private CharBuffer HTML;
	
	private void setHTML(FileReader reader) {
		try {
			reader.read(HTML);
			System.out.println(HTML);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
//		EmailManager manager = new EmailManager();
//		manager.setHTML(new FileReader());
//		System.out.println(manager.HTML);
		
		URL url = EmailManager.class.getResource("/WebContent/readme.md");
		
		System.out.println(EmailManager.class.getClassLoader().getResource("/"));
		
//		System.out.println(ClassLoader.getSystemResource("/com/BookSystem/MailManager/Test.java"));
	}
	
	private EmailManager() {
		// 获取系统属性
		properties = System.getProperties();

		properties.put("mail.smtp.auth", "true");
		// 设置邮件服务器
		properties.setProperty("mail.smtp.host", host);
		
		properties.setProperty("mail.user", "16240011@mail.szpt.edu.cn");
		properties.setProperty("mail.password", "Sz09043330");
		
		authenticator = new Authenticator() {
			 @Override
			 protected PasswordAuthentication getPasswordAuthentication() {
			     // 用户名、密码
			     String userName = properties.getProperty("mail.user");
			     String password = properties.getProperty("mail.password");
			     return new PasswordAuthentication(userName, password);
			 }
		};
		// 获取默认session对象
        session = Session.getInstance(properties, authenticator);
	}
	
	public boolean sendMessage(String to,String title) {
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
			message.setSubject(title);

			// 设置消息体
	        message.setContent("<h1>This is actual message</h1><ol><li>a</li><li>a</li><li>a</li><li>a</li></ol>",
                     "text/html" );

			// 发送消息
			Transport.send(message);
			
		} catch (MessagingException mex) {
			mex.printStackTrace();
			return false;
		}
		
		return true;
	}
}
