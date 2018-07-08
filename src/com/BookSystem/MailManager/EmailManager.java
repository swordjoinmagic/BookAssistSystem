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

	// 发件人电子邮箱
	private String from = "16240011@mail.szpt.edu.cn";

	// 指定发送邮件的主机为 localhost
	private String host = "smtp.exmail.qq.com";
	
	// 服务器配置的一些配置
	private Properties properties;
	private Authenticator authenticator;
	private Session session;
	
	// HTML文本
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
	        message.setContent(HTML,
                     "text/html;charset=utf-8" );

			// 发送消息
			Transport.send(message);
			
		} catch (MessagingException mex) {
			mex.printStackTrace();
			return false;
		}
		
		return true;
	}

	// 发送自动续借的邮箱提醒
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
				"/*==========搜索界面============*/" + 
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
				"/*============登录界面与logo处CSS===============*/" + 
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
				"/*===============书籍详情页面===========*/" + 
				".baseInformation{" + 
				"	width: 100%;" + 
				"	height: 100%;" + 
				"    display: block;" + 
				"    margin-bottom: 60px;" + 
				"    padding: 10px;" + 
				"}" + 
				"/*书籍详情页面的主要内容的CSS*/" + 
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
				"	width: 2２0px;" + 
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
				"/*===================设置特别关注================*/" + 
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
				"/*======================登录界面==================*/" + 
				".loginGraphic{" + 
				"	background: whitesmoke;" + 
				"" + 
				"}" + 
				".loginContent{" + 
				"	width: 300px;" + 
				"}" + 
				"" + 
				"" + 
				"/* ===============书籍详情界面========================== */" + 
				".wordCloud{" + 
				"	display: none;" + 
				"}" + 
				"" + 
				"/* ================设置界面======================= */" + 
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
				"/* =================登录界面================================ */" + 
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
				"/* ===================书籍搜索界面============================== */" + 
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
				"		<td colspan=\"4\">续借成功的书籍如下:</td>" + 
				"		<td colspan=\"3\"></td>" + 
				"		<td colspan=\"4\">续借失败的书籍如下:(失败原因可能为已经到达最大续借次数或在寒暑假期间)</td>" + 
				"	</tr>" + 
				"	<tr>" + 
				"		<td>书名</td>" + 
				"		<td>藏书分管</td>" + 
				"		<td>还书日期</td>" + 
				"		<td></td>" + 
				"		<td></td>" + 
				"		<td></td>" + 
				"		<td>书名</td>" + 
				"		<td>藏书分管</td>" + 
				"		<td>还书日期</td>" + 
				"	</tr>" + 
				std + 
				"</table>";
		this.setHTML(html);
		this.sendMessage(to, "自动续借通知");
	}

	// 发送新书速递的邮箱提醒
	public void sendnewBookTipsEmail(String to,Document jsonObject) {
//		for(String key:jsonObject.keySet()) {
//			List<Document>
//		}
	}
}
