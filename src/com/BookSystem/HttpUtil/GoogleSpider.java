package com.BookSystem.HttpUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.bson.Document;
import org.json.JSONObject;

import com.BookSystem.Decryption.MyDecryptionUtil;
import com.BookSystem.ExcScript.ExecuteScript;

public class GoogleSpider {
	
	private static String header = "{" + 
			"            'Accept-Encoding': 'identity;q=1, *;q=0'," + 
			"            'chrome-proxy': 'frfr'," + 
			"            'Range': 'bytes=0-'," + 
			"            'Referer': 'https://translate.google.cn/'," + 
			"            'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36'" + 
			"        }";
	
	public static InputStream getAudio(String content) throws UnsupportedEncodingException {
		
		// 第一步，解密
		content = MyDecryptionUtil.getDecodeString(content);
		
		System.out.println("解密后的数据:"+content);
		
		String tk = ExecuteScript.getExecuteScript().getTK(content);
		
		String url = "https://translate.google.cn/translate_tts?ie=UTF-8&q=%s&tl=zh-CN&total=1&idx=0&textlen=%s&tk=%s&client=t&prev=input";
				
		Document jsonObject = Document.parse(header);
		
//		InputStream inputStream = HttpClientUtil.get(String.format(url, content,content.length(),tk), jsonObject);
		byte[] bytes = HttpClientUtil.get(String.format(url, content,content.length(),tk), jsonObject);
		
		
		try {
			FileOutputStream outputStream = new FileOutputStream(new File("D:\\JavaEEInstance\\BookAssitantSystem\\BookAssitantSystem\\src\\com\\BookSystem\\HttpUtil\\a.mpeg"));
			outputStream.write(bytes,0,bytes.length);
			outputStream.flush();
			outputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	public static void main(String[] args) {
		try {
			getAudio("5ZO85ZO85ZOI5Zi/5b+r5L2/55So5Y+M5oiq5qON77yM5ZO85ZO85ZOI5Zi/5b+r5L2/55So5Y+M" + 
					"5oiq5qONLOi/memmluatjOeahOWOn+WUseaYr+iwgQ==");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

