package com.BookSystem.Decryption;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.DigestUtils;

import com.mysql.jdbc.util.Base64Decoder;
import com.sun.org.apache.xerces.internal.impl.xpath.XPath.Step;

// ���ڽ��ܵĹ�����
public class MyDecryptionUtil {
	/*
	 * 
	 * @param encryptionCode ��ǰ���ṩ�ļ�����
	 * @return
	 */
	public static Date getDecryptionDate(String encryptionCode) {
		// �����base64���ܵõ���bytes����
		byte[] bytes = Base64Decoder.decode(encryptionCode.getBytes(), 0, encryptionCode.length());
		
		// ��ý��ܵõ����ַ���
		String string = new String(bytes);
		
		List<Integer>list = new ArrayList<Integer>();
		
		for(int i=0;i<string.length();i++) {
			list.add(string.charAt(i)-65);
		}
	
		// ���ʱ���
		String result = "";
		for(int i:list) {
			result += i;
		}
		
		// ��������
		Date date = new Date(Long.parseLong(result));

		return date;
	}
	
	/*
	 * �ж�base64һ����,һ�·���true
	 */
	public static boolean isConsistent(String encryptionCode) {
		Date jsDate = getDecryptionDate(encryptionCode);
		Date nowDate = new Date();
		
		// �ж���
		if(nowDate.getYear() != jsDate.getYear())
			return false;
		// �ж���
		if(nowDate.getMonth() != jsDate.getMonth()) 
			return false;
		// �ж���
		if(nowDate.getDay() != jsDate.getDay()) 
			return false;
		// �ж�ʱ
		if(nowDate.getHours() != jsDate.getHours()) 
			return false;
		// �жϷ�
		if(nowDate.getMinutes() != jsDate.getMinutes())
			return false;
		// һ��
		return true;
	}

	/**
	 * �ж�ǰ�˸����ļ��ܴ��Ƿ���û�������һ��
	 * @param userName
	 * @param encryptionCode
	 * @return
	 */
	public static boolean isConsistentByMd5(String userName,String encryptionCode) {
		
		String encryption = "";
		
		for(int i=0;i<userName.length();i++) {
			// �����ַ��ȱ�����֣�Ȼ������Ӣ�ĵ�abcd��ĸ
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
