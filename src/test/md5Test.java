package test;

import java.util.Date;

import org.springframework.util.DigestUtils;

import com.mysql.jdbc.util.Base64Decoder;

import sun.misc.BASE64Encoder;

public class md5Test {
	public static void main(String[] args) {				
		String string = DigestUtils.md5DigestAsHex("12345".getBytes());
		System.out.println(string);
		
	}

}
