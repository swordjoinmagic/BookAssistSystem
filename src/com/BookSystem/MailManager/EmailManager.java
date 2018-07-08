package com.BookSystem.MailManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.CharBuffer;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import com.BookSystem.ApplicationContextUtil.ContextUtil;

public class EmailManager {
	
	public static final EmailManager EMAIL_MANAGER = new EmailManager();
	
	public static EmailManager getEmailManager() {
		return EMAIL_MANAGER;
	}

	// �����˵�������
	private String from = "16240011@mail.szpt.edu.cn";

	// ָ�������ʼ�������Ϊ localhost
	private String host = "smtp.exmail.qq.com";
	
	// ���������õ�һЩ����
	private Properties properties;
	private Authenticator authenticator;
	private Session session;
	
	// HTML�ı�
	private String HTML;
	
	private void setHTML(String string) {
		this.HTML = string;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		EmailManager manager = EmailManager.getEmailManager();
//		manager.sendAutoBorrowTipsEmail("16240011@mail.szpt.edu.cn");
		manager.setHTML("sadsad");
		manager.sendMessage("16240011@mail.szpt.edu.cn", "bookAssiantSystem");
//		manager.setHTML(new FileReader());
//		System.out.println(manager.HTML);
		
//		URL url = EmailManager.class.getResource("/WebContent/readme.md");
		
//		System.out.println(EmailManager.class.getClassLoader().getResource("/"));
		
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
	        message.setContent(HTML,
                     "text/html;charset=utf-8" );

			// ������Ϣ
			Transport.send(message);
			
		} catch (MessagingException mex) {
			mex.printStackTrace();
			return false;
		}
		
		return true;
	}

	// �����Զ��������������
	public void sendAutoBorrowTipsEmail(String to,JSONArray success,JSONArray fails) {
		String std = "";

		for(int i=0;i<20;i++) {
			String successBookName;
			String successBookbranchLibray;
			String successPayDay;
			try {
				JSONObject jsonObject = success.getJSONObject(i); 
				successBookName = jsonObject.getString("name");
				successBookbranchLibray = jsonObject.getString("branchLibray");
				successPayDay = jsonObject.getString("repayDay");
			}catch(Exception e) {
				successBookName = "";
				successPayDay = "";
				successBookbranchLibray = "";
			}
			String failBookName;
			String failBookbranchLibray;
			String failPayDay;
			try {
				JSONObject jsonObject = fails.getJSONObject(i); 
				failBookName = jsonObject.getString("name");
				failBookbranchLibray = jsonObject.getString("branchLibray");
				failPayDay = jsonObject.getString("repayDay");
			}catch(Exception e) {
				failBookName = "";
				failPayDay = "";
				failBookbranchLibray = "";
			}
			
			
			String td = "	<tr>" + 
					"		<td>"+successBookName+"</td>" + 
					"		<td>"+successBookbranchLibray+"</td>" + 
					"		<td>"+successPayDay+"</td>" + 
					"		<td></td>" + 
					"		<td></td>" + 
					"		<td></td>" + 
					"		<td>"+failBookName+"</td>" + 
					"		<td>"+failBookbranchLibray+"</td>" + 
					"		<td>"+failPayDay+"</td>" +  
					"	</tr>";
			std += td;
		}
		
		String html = "<style>" + 
				"	p{" + 
				"	margin: 0px;" + 
				"}" + 
				".queryAssistance{" + 
				"	/*border: solid black 1px;*/" + 
				"	" + 
				"}" + 
				".B{" + 
				"	text-align: center;" + 
				"}" + 
				".C{" + 
				"	width: 100%;" + 
				"	height: 100%;" + 
				"}" + 
				".widget{" + 
				"	margin-bottom: 30px;" + 
				"}" + 
				".searchResult{" + 
				"	width: 60%;" + 
				"	float: left;" + 
				"}" + 
				".bookDetail{" + 
				"	height: 200px;" + 
				"}" + 
				".bookDetailLabel{" + 
				"	width: 100px;	" + 
				"	vertical-align: top;" + 
				"}" + 
				".bookDetailContent{" + 
				"	width: 160px;" + 
				"}" + 
				".bookImg{" + 
				"	float: left;" + 
				"	margin-right: 20px;" + 
				"}" + 
				"" + 
				".dividePage{" + 
				"	margin: 0px;" + 
				"	width: 100%;" + 
				"	height: 100%;" + 
				"}" + 
				".right{" + 
				"	float: right;" + 
				"}" + 
				"" + 
				"/*==========��������============*/" + 
				".ratingScore{" + 
				"	font-size: 12px;" + 
				"}" + 
				".book{" + 
				"	/*border-bottom: solid 1px black;*/" + 
				"	padding-bottom: 10px;" + 
				"	margin-bottom: 5%;" + 
				"	margin-top: 5%;" + 
				"}" + 
				"" + 
				"/*============��¼������logo��CSS===============*/" + 
				".searchBar{" + 
				"	text-align: center;" + 
				"}" + 
				"#searchInput{" + 
				"	width: 40%;" + 
				"}" + 
				".topy{" + 
				"	position: absolute;" + 
				"	top: 0px;" + 
				"	right: 0px;" + 
				"	font-size: 12px;" + 
				"}" + 
				".bookDetailTable{" + 
				"	/*width: 100%;" + 
				"	height: 100%;*/" + 
				"}" + 
				".setup{" + 
				"	list-style:none;" + 
				"	position: absolute;" + 
				"	top: 20px;" + 
				"	right: 10px;" + 
				"	display: none;" + 
				"}" + 
				".setup li{" + 
				"	height: 20px;" + 
				"	width: 90px;" + 
				"	color: black;" + 
				"	background: white;" + 
				"	font-size: 13px;" + 
				"	border-bottom: 1px #DDD double;" + 
				"	z-index: 301;" + 
				"}" + 
				".setup li:hover{" + 
				"	height: 20px;" + 
				"	width: 90px;" + 
				"	color: black;" + 
				"	background:lightgray;" + 
				"	font-size: 13px;" + 
				"	border-bottom: 1px #DDD double;" + 
				"	z-index: 301;" + 
				"	cursor:pointer;" + 
				"}" + 
				"" + 
				"/*===============�鼮����ҳ��===========*/" + 
				".baseInformation{" + 
				"	width: 100%;" + 
				"	height: 100%;" + 
				"    display: block;" + 
				"    margin-bottom: 60px;" + 
				"    padding: 10px;" + 
				"}" + 
				"/*�鼮����ҳ�����Ҫ���ݵ�CSS*/" + 
				".mainContent{" + 
				"	margin-bottom: 20px;" + 
				"}" + 
				".bookDetailImg{" + 
				"	width: 150px;" + 
				"	height: 200px;" + 
				"}" + 
				".bookBaseInformation{" + 
				"	/*width: 100%;*/" + 
				"	/*height: 100%;*/" + 
				"	font-size: 18px;" + 
				"}" + 
				".rating{" + 
				"	position: absolute;" + 
				"	top:10px;" + 
				"	right: 0px;" + 
				"	width: 2��0px;" + 
				"	height: 100px;" + 
				"}" + 
				".bookContent{" + 
				"	margin-top: 20px;" + 
				"}" + 
				".bookCatalogue{" + 
				"	margin-top: 20px;" + 
				"}" + 
				".bookLabel{" + 
				"	color: green;" + 
				"	font-size: 20px;" + 
				"}" + 
				"span.cleaned{" + 
				"	margin-left: 15px;" + 
				"	margin-right: 15px;" + 
				"}" + 
				"" + 
				".shortintro{" + 
				"	display: inline;" + 
				"}" + 
				".longintro{" + 
				"	display: none;" + 
				"}" + 
				".longCatalog{" + 
				"	display: none;" + 
				"}" + 
				"a.watchMore:hover{" + 
				"	background: deepskyblue;" + 
				"}" + 
				".bookRecommand{" + 
				"	width: 180px;" + 
				"	height: 180px;" + 
				"	display: inline-block;" + 
				"}" + 
				".wordWithImg{" + 
				"	display: inline-block;" + 
				"	width: 150px;" + 
				"	height: 180px;" + 
				"}" + 
				".str{" + 
				"	width: 150px;" + 
				"	display: inline-block;" + 
				"	text-align: center;" + 
				"}" + 
				"" + 
				"/*===================�����ر��ע================*/" + 
				".specialAttention{" + 
				"	border: 1px gray solid;" + 
				"	margin: 10px;" + 
				"	margin-top: 10px;" + 
				"	margin-bottom: 30px;" + 
				"}" + 
				"span.specialLabel{" + 
				"	color: red;" + 
				"	background: gainsboro;" + 
				"}" + 
				".margintop{" + 
				"	margin-bottom: 150px;" + 
				"}" + 
				"" + 
				"/*======================��¼����==================*/" + 
				".loginGraphic{" + 
				"	background: whitesmoke;" + 
				"" + 
				"}" + 
				".loginContent{" + 
				"	width: 300px;" + 
				"}" + 
				"" + 
				"" + 
				"/* ===============�鼮�������========================== */" + 
				".wordCloud{" + 
				"	display: none;" + 
				"}" + 
				"" + 
				"/* ================���ý���======================= */" + 
				"#basesetup,#attention,#history,#freenotice{" + 
				"	cursor:pointer;" + 
				"}" + 
				".historyModel td{" + 
				"	width: 300px;" + 
				"	margin: 40px;" + 
				"	padding: 10px;" + 
				"	text-align: center;" + 
				"}" + 
				"span.prePage{" + 
				"	cursor:pointer;" + 
				"}" + 
				"span.nextPage{" + 
				"	float: right;" + 
				"	cursor:pointer;" + 
				"}" + 
				"" + 
				"/* =================��¼����================================ */" + 
				".loginNew span{" + 
				"	margin-left: 30px;" + 
				"	margin-right: 30px;" + 
				"	" + 
				"	text-align: right;	" + 
				"}" + 
				".tipsMessageLogin{" + 
				"	display: inline;" + 
				"}" + 
				"#getPosition{" + 
				"	padding: 70px;" + 
				"}" + 
				".usernameDiv,.passwordDiv,.verificationCodeDiv{" + 
				"	width:40%;" + 
				"}" + 
				"" + 
				".loginNew input{" + 
				"	float: right;" + 
				"}" + 
				"" + 
				"/* ===================�鼮��������============================== */" + 
				".bookCollectionTable td.labelCollection{" + 
				"	padding-left: 40px;" + 
				"}" + 
				"" + 
				"</style>" + 
				"" + 
				"<head>" + 
				"	<meta charset=\"utf-8\">" + 
				"</head>" + 
				"<table class=\"historyModel\" style=\"background: ghostwhite\">" + 
				"	<tr>" + 
				"		<td colspan=\"4\">����ɹ����鼮����:</td>" + 
				"		<td colspan=\"3\"></td>" + 
				"		<td colspan=\"4\">����ʧ�ܵ��鼮����:(ʧ��ԭ�����Ϊ�Ѿ������������������ں�����ڼ�)</td>" + 
				"	</tr>" + 
				"	<tr>" + 
				"		<td>����</td>" + 
				"		<td>����ֹ�</td>" + 
				"		<td>��������</td>" + 
				"		<td></td>" + 
				"		<td></td>" + 
				"		<td></td>" + 
				"		<td>����</td>" + 
				"		<td>����ֹ�</td>" + 
				"		<td>��������</td>" + 
				"	</tr>" + 
				std + 
				"</table>";
		this.setHTML(html);
		this.sendMessage(to, "�Զ�����֪ͨ");
	}

	// ���������ٵݵ���������
	public void sendnewBookTipsEmail(String to,Document jsonObject) {
//		for(String key:jsonObject.keySet()) {
//			List<Document>
//		}
	}
}
