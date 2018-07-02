package test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringIOCTest {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		ApplicationContext context2 = new ClassPathXmlApplicationContext();
		Source source = context.getBean(Source.class);
		System.out.println(source);
	}
}
