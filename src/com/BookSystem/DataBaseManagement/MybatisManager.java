package com.BookSystem.DataBaseManagement;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSessionFactory;

public class MybatisManager {
	private static SqlSessionFactory sqlSessionFactory;
	static {
		String resource = "mybatis-config.xml";
//		InputStream
	}
}
