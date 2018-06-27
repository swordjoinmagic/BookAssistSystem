package com.BookSystem.DataBaseManagement;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;
import java.util.*;
public class MongoDBCommentDataBase {
	private static final MongoDBCommentDataBase dao = new MongoDBCommentDataBase();
	
	private MongoClient mongoClient;
	private MongoDatabase database;
	
	public static MongoDBCommentDataBase getDao(){
		return dao;
	}
	
	private MongoDBCommentDataBase() {
		// 连接mongodb服务
		mongoClient = new MongoClient("localhost",27017);
		
		// 连接到mongodb数据库
		database = mongoClient.getDatabase("pythonLessonExamData");
	}
	
	public MongoCollection<Document> getCollection(String name) {
		MongoCollection<Document> mongoCollection = database.getCollection(name);
		return mongoCollection;
	}
	
	public FindIterable<Document> find(MongoCollection<Document>collection,Document findData) {
		return collection.find(findData);
	}
	
}
