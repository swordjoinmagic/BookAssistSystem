package com.BookSystem.Decryption;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.util.Base64Decoder;
import com.sun.org.apache.xerces.internal.impl.xpath.XPath.Step;

// 用于解密的工具类
public class MyDecryptionUtil {
	/*
	 * 
	 * @param encryptionCode 由前端提供的加密码
	 * @return
	 */
	public static Date getDecryptionDate(String encryptionCode) {
		// 获得用base64解密得到的bytes数组
		byte[] bytes = Base64Decoder.decode(encryptionCode.getBytes(), 0, encryptionCode.length());
		
		// 获得解密得到的字符串
		String string = new String(bytes);
		
		List<Integer>list = new ArrayList<Integer>();
		
		for(int i=0;i<string.length();i++) {
			list.add(string.charAt(i)-65);
		}
	
		// 获得时间戳
		String result = "";
		for(int i:list) {
			result += i;
		}
		
		// 生成日期
		Date date = new Date(Long.parseLong(result));

		return date;
	}
	
	/*
	 * 判断一致性,一致返回true
	 */
	public static boolean isConsistent(String encryptionCode) {
		Date jsDate = getDecryptionDate(encryptionCode);
		Date nowDate = new Date();
		
		// 判断年
		if(nowDate.getYear() != jsDate.getYear())
			return false;
		// 判断月
		if(nowDate.getMonth() != jsDate.getMonth()) 
			return false;
		// 判断日
		if(nowDate.getDay() != jsDate.getDay()) 
			return false;
		// 判断时
		if(nowDate.getHours() != jsDate.getHours()) 
			return false;
		// 判断分
		if(nowDate.getMinutes() != jsDate.getMinutes())
			return false;
		// 一致
		return true;
	}
	
	public static void main(String[] args) {
		System.out.println(isConsistent("QkZEQUdERUZFSEdCRA=="));
	}
}
