package test;

import java.util.Random;

public class TestInsertQuesion {
	private static int answer = 0;
	private static String expressStr = "";
	private static Random random = new Random();
	public static void main(String[] args) {
		int a = random.nextInt(1000);
		int b = random.nextInt(1000);
		String question = a+"+"+b+"µÈÓÚ¼¸";
		String answer = String.valueOf((a+b));
		System.out.println(question);
		System.out.println(answer);
	}
}
