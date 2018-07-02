package com.BookSystem.MailManager;

import javax.mail.*;

//�ļ��� SendEmail.java

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Test {
	
	public static void main(String[] args) {
		// �ռ��˵�������
		String to = "16240011@mail.szpt.edu.cn";

		// �����˵�������
		String from = "16240011@mail.szpt.edu.cn";

		// ָ�������ʼ�������Ϊ localhost
		String host = "smtp.exmail.qq.com";

		// ��ȡϵͳ����
		Properties properties = System.getProperties();

		properties.put("mail.smtp.auth", "true");
		// �����ʼ�������
		properties.setProperty("mail.smtp.host", host);
		
		properties.setProperty("mail.user", "16240011@mail.szpt.edu.cn");
		properties.setProperty("mail.password", "Sz09043330");
		
		Authenticator authenticator = new Authenticator() {
		             @Override
		             protected PasswordAuthentication getPasswordAuthentication() {
		                 // �û���������
		                 String userName = properties.getProperty("mail.user");
		                 String password = properties.getProperty("mail.password");
		                 return new PasswordAuthentication(userName, password);
		             }
		         };

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
			message.setSubject("This is the Subject Line!");

			// ������Ϣ��
	        message.setContent("<h1>This is actual message</h1><ol><li>a</li><li>a</li><li>a</li><li>a</li></ol>",
                     "text/html" );

			// ������Ϣ
			Transport.send(message);
			
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
