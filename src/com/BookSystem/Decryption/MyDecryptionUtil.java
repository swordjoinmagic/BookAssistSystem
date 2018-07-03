package com.BookSystem.Decryption;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	 * �ж�һ����,һ�·���true
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
	
	public static void main(String[] args) {
		System.out.println(isConsistent("QkZEQUdERUZFSEdCRA=="));
	}
}
