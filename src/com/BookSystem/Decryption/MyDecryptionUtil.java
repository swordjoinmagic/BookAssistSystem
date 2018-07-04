package com.BookSystem.Decryption;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.DigestUtils;

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
	 * 判断base64一致性,一致返回true
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

	/**
	 * 判断前端给出的加密串是否跟用户名保持一致
	 * @param userName
	 * @param encryptionCode
	 * @return
	 */
	public static boolean isConsistentByMd5(String userName,String encryptionCode) {
		
		String encryption = "";
		
		for(int i=0;i<userName.length();i++) {
			// 将该字符先变成数字，然后将其变成英文的abcd字母
			int temp = userName.charAt(i)-48 + 65;
			encryption += String.valueOf(temp);
		}
				
		String userNameEncryption = DigestUtils.md5DigestAsHex(encryption.getBytes());
			
		if(encryptionCode.equals(userNameEncryption)) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
//		System.out.println(isConsistent("QkZEQUdERUZFSEdCRA=="));
		System.out.println(isConsistentByMd5("16240011", "80ab89d97ec488c1bc19bc8b5b1cbc73"));
	}
}
