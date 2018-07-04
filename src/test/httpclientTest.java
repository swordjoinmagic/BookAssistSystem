package test;

import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.bson.Document;
import org.json.JSONObject;
import org.json.JSONTokener;

public class httpclientTest {
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
			throw new RuntimeException(e);
		}finally {
			client.getConnectionManager().shutdown();
		}
        return json;
	}
	public static void main(String[] args) {
		String url = "http://localhost:8080/BookAssitantSystem/test/test2";
		System.out.println(get(url));
	}
}
