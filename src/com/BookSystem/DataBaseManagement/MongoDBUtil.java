package com.BookSystem.DataBaseManagement;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

public class MongoDBUtil {
	public static Document AddOrCondition(Document result,Document condition) {
		List<Document> add = result.get("$or", ArrayList.class);
		add.add(condition);
		return result;
	}
}
