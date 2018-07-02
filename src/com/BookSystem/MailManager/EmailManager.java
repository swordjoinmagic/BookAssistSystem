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
	
	// �����˵�������
	private String from = "16240011@mail.szpt.edu.cn";

	// ָ�������ʼ�������Ϊ localhost
	private String host = "smtp.exmail.qq.com";
	
	// ���������õ�һЩ����
	private Properties properties;
	private Authenticator authenticator;
	private Session session;
	
	// HTML�ı�
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
		// ��ȡϵͳ����
		properties = System.getProperties();

		properties.put("mail.smtp.auth", "true");
		// �����ʼ�������
		properties.setProperty("mail.smtp.host", host);
		
		properties.setProperty("mail.user", "16240011@mail.szpt.edu.cn");
		properties.setProperty("mail.password", "Sz09043330");
		
		authenticator = new Authenticator() {
			 @Override
			 protected PasswordAuthentication getPasswordAuthentication() {
			     // �û���������
			     String userName = properties.getProperty("mail.user");
			     String password = properties.getProperty("mail.password");
			     return new PasswordAuthentication(userName, password);
			 }
		};
		// ��ȡĬ��session����
        session = Session.getInstance(properties, authenticator);
	}
	
	public boolean sendMessage(String to,String title) {
		// ��ȡĬ��session����
        Session session = Session.getInstance(properties, authenticator);
		try {
			// ����Ĭ�ϵ� MimeMessage ����
			MimeMessage message = new MimeMessage(session);

			// Set From: ͷ��ͷ�ֶ�
			message.setFrom(new InternetAddress(from));

			// Set To: ͷ��ͷ�ֶ�
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: ͷ��ͷ�ֶ�
			message.setSubject(title);

			// ������Ϣ��
	        message.setContent("<h1>This is actual message</h1><ol><li>a</li><li>a</li><li>a</li><li>a</li></ol>",
                     "text/html" );

			// ������Ϣ
			Transport.send(message);
			
		} catch (MessagingException mex) {
			mex.printStackTrace();
			return false;
		}
		
		return true;
	}
}
