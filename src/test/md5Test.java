package test;

import java.util.Date;

import org.springframework.util.DigestUtils;

import com.mysql.jdbc.util.Base64Decoder;

import sun.misc.BASE64Encoder;

public class md5Test {
	public static void main(String[] args) {				
		Date date = new Date();
		System.out.println(date);
		System.out.println(date.getTime());
		Date date2 = new Date();
		BASE64Encoder base64Encoder = new BASE64Encoder();
		String mString = base64Encoder.encode("1234566".getBytes());
		byte[] aString = Base64Decoder.decode(mString.getBytes(), 0, mString.length());
		System.out.println("mString:"+mString+" aString:"+new String(aString));
	}

}
