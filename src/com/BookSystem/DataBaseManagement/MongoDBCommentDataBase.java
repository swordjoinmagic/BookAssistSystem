package com.BookSystem.DataBaseManagement;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.*;

public class MongoDBCommentDataBase {
	private static final MongoDBCommentDataBase dao = new MongoDBCommentDataBase();
	
	private MongoClient mongoClient;
	private MongoDatabase database;
	// 默认集合
	private MongoCollection<Document>defaultCollection;
	
	public static MongoDBCommentDataBase getDao(){
		return dao;
	}
	
	private MongoDBCommentDataBase() {
		// 连接mongodb服务
		mongoClient = new MongoClient("localhost",27017);
		
		// 连接到mongodb数据库
		database = mongoClient.getDatabase("pythonLessonExamData");
		
		defaultCollection = database.getCollection("BookData");
	}
	
	public MongoCollection<Document> getCollection(String name) {
		MongoCollection<Document> mongoCollection = database.getCollection(name);
		return mongoCollection;
	}
	
	public FindIterable<Document> find(MongoCollection<Document>collection,Document findData) {
		return collection.find(findData);
	}
	
	public FindIterable<Document>findWithDefaultCollection(Document findData){
		return defaultCollection.find(findData);
	}
	
	public long getCount(Document findData) {
		return defaultCollection.count(findData);
	}
}
