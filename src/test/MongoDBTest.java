package test;


import java.util.*;

import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonElement;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.BookSystem.DataBaseManagement.MongoDBCommentDataBase;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class MongoDBTest {
	public static String jsonSortType0 = "{" + 
			"'ratingAverage':-1," + 
			"'publishYear':-1," + 
			"'ratingNumberRaters':-1" + 
			"}";

	public static void main(String[] args) {
		MongoDBCommentDataBase dao = MongoDBCommentDataBase.getDao();
//		Document document = Document.parse("{'catalog': {'$regex': 'Java', '$options': 'i'}}");
//		
//		System.out.println(document.toJson());
//		
//		FindIterable<Document> result	= dao.find(dao.getCollection("BookData"), document).limit(10);
		
		String jsonBookNameKey = "{'bookName': {'$regex': '%s', '$options': 'i'}}";
		
		Document sortData = Document.parse(jsonSortType0);
		Document findData = Document.parse(String.format(jsonBookNameKey, "Java"));
		
		FindIterable<Document> result = dao.findWithDefaultCollection(findData).sort(sortData).limit(10);
		
		System.out.println(dao.getCount(findData));
		
		for(Document document : result) {
			System.out.println(
					"  ratingNumberRaters:"+document.getInteger("ratingNumberRaters")+
					"  Year:"+document.getInteger("publishYear")+
					"  rating:"+document.getDouble("ratingAverage"));
		}
	}
}
