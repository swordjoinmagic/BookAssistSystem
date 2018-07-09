package com.BookSystem.HttpUtil;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.bson.Document;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * 用于执行Http请求的工具类
 * @author Administrator
 *
 */
public class HttpClientUtil {
	public static JSONObject get(String url) {
		HttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		JSONObject json = null;
		try {
            HttpResponse res = client.execute(get);   
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {   
                HttpEntity entity = res.getEntity();   
                json = new JSONObject(new JSONTokener(new InputStreamReader(entity.getContent(), HTTP.UTF_8)));   
            } 
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			client.getConnectionManager().shutdown();
		}
        return json;
	}
	
	public static byte[] get(String url,Document header) {
		HttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		
		for(String key : header.keySet()) {
			get.setHeader(key, header.getString(key));
		}
		
		JSONObject json = null;
		try {
            HttpResponse res = client.execute(get);   
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {   
                HttpEntity entity = res.getEntity();   
                System.out.println("返回content");
                System.out.println(entity.getContentLength());
                
                byte[] bytes = new byte[(int) entity.getContentLength()];
                entity.getContent().read(bytes);
                
                return bytes;
            } 
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			client.getConnectionManager().shutdown();
		}
        return null;
	}
}
