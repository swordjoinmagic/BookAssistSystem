package com.BookSystem.controller;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.security.Escape;
import org.bson.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.BookSystem.DataBaseManagement.MongoDBCommentDataBase;
import com.BookSystem.DataBaseManagement.MongoDBUtil;
import com.mongodb.client.FindIterable;
import com.sun.swing.internal.plaf.basic.resources.basic;
import com.sun.xml.internal.ws.api.ha.StickyFeature;

@Controller
@RequestMapping
public class BookSearchController {
	
	// 查询字典的字符串表示
	private String jsonOrAllKeysButNotCatalog = "{'$or':[" + 
			"{'bookName':{'$regex':'%1$s','$options':'i'}}," + 
			"{'content': {'$regex': '%1$s', '$options': 'i'}}," + 
			"{'author': {'$regex': '%1$s', '$options': 'i'}}," + 
			"{'ISBN': {'$regex': '%1$s', '$options': 'i'}}," + 
			"{'publishYear': {'$regex': '%1$s', '$options': 'i'}}," + 
			"{'index': {'$regex': '%1$s', '$options': 'i'}}," + 
			"{'publisher': {'$regex': '%1$s', '$options': 'i'}}," + 
			"{'systemNumber': {'$regex': '%1$s', '$options': 'i'}}" + 
			"]}";
	private String jsonCatalogKey = "{'catalog': {'$regex': '%s', '$options': 'i'}}";
	private String jsonBookNameKey = "{'bookName': {'$regex': '%s', '$options': 'i'}}";
	private String jsonBookAuthorKey = "{'author': {'$regex': '%s', '$options': 'i'}}";
	private String jsonBookPublisherKey = "{'publisher': {'$regex': '%s', '$options': 'i'}}";
	private String jsonISBNKey = "{'ISBN': {'$regex': '%s', '$options': 'i'}}";
	private String jsonIndexKey = "{'index': {'$regex': '%s', '$options': 'i'}}";
	private String jsonSystemNumberKey = "{'systemNumber':{'$regex': '%s', '$options': 'i'}}";
	private String jsonAllKey = "{'$or':[" + 
			"{'catalog': {'$regex': '%1$s', '$options': 'i'}}," + 
			"{'bookName':{'$regex':'%1$s','$options':'i'}}," + 
			"{'content': {'$regex': '%1$s', '$options': 'i'}}," + 
			"{'author': {'$regex': '%1$s', '$options': 'i'}}," + 
			"{'ISBN': {'$regex': '%1$s', '$options': 'i'}}," + 
			"{'publishYear': {'$regex': '%1$s', '$options': 'i'}}," + 
			"{'index': {'$regex': '%1$s', '$options': 'i'}}," + 
			"{'publisher': {'$regex': '%1$s', '$options': 'i'}}," + 
			"{'systemNumber':{'$regex': '%1$s', '$options': 'i'}}" + 
			"]}";
	
	// 排序查询字典字符串表示
	public static String jsonSortType0 = "{" + 
			"'publishYear':-1," + 
			"'ratingAverage':-1," + 
			"'ratingNumberRaters':-1" + 
			"}";
	private String jsonSortType1 = "{" + 
			"'ratingAverage':-1," + 
			"'publishYear':-1," + 
			"'ratingNumberRaters':-1" + 
			"}";
	private String jsonSortType2 = "{" + 
			"'ratingNumberRaters':-1," + 
			"'publishYear':-1," + 
			"'ratingAverage':-1" + 
			"}";
	
	// 一页有多少条数据
	private int limitCount = 10;
	
	/**
	 * 
	 * @param page 当前页数 
	 * @param sortType 当前排序类型，
	 * 0：年-评分-评论人数，
	 * 1：评分-年-评论人数，
	 * 2：评论人数-年-评分
	 * @param searchType 当前搜索的字段,分为，所有字段、目录、作者、书名、ISBN号等等
	 * 					 分别是：
	 * 					"AllKeyButNotCatalog" - 所有字段（除目录）
	 * 					"AllKey" 			  - 所有字段
	 * 					"CatalogKey"		  - 目录
	 * 					"BookNameKey"		  - 书名
	 * 					"BookAuthorKey"		  - 作者
	 * 					"BookPublisherKey"	  - 出版社
	 * 					"ISBNKey"			  - ISBN
	 * 					"IndexKey"			  - 索书号
	 * 					"SystemNumberKey"	  - 系统号
	 * @param queryContent: 查询的内容
	 * @return
	 */
	@RequestMapping(path="/search",method=RequestMethod.GET)
	public ModelAndView search(
			@RequestParam(name="page",required=false,defaultValue="0") int page,
			@RequestParam(name="sortType",required=false,defaultValue="1") int sortType,
			@RequestParam(name="searchType",required=false,defaultValue="") String searchType,
			@RequestParam(name="queryContent",required=false,defaultValue="") String queryContent) {
		
		// 构造视图-模型
		ModelAndView view = new ModelAndView();
		
		// 向数据库中取数据
		MongoDBCommentDataBase ado = MongoDBCommentDataBase.getDao();
		
		// 初始化查询结果
		FindIterable<Document>result = null;
		
		// 根据sortType和searchType构造查询Document
		Document findData;		// 查询字典
		
		// 根据searchType构造查询字典
		switch(searchType) {
			case "AllKey":
				// 所有字段
				findData = Document.parse(String.format(jsonAllKey, queryContent));
				result = ado.findWithDefaultCollection(findData);
				break;
			case "CatalogKey":
				// 目录
				findData = Document.parse(String.format(jsonCatalogKey, queryContent));
				result = ado.findWithDefaultCollection(findData);
				break;
			case "BookNameKey":
				// 书名
				findData = Document.parse(String.format(jsonBookNameKey, queryContent));
				result = ado.findWithDefaultCollection(findData);
				break;
			case "BookAuthorKey":
				// 作者
				findData = Document.parse(String.format(jsonBookAuthorKey, queryContent));
				result = ado.findWithDefaultCollection(findData);
				break;
			case "BookPublisherKey":
				// 出版社
				findData = Document.parse(String.format(jsonBookPublisherKey, queryContent));
				result = ado.findWithDefaultCollection(findData);
				break;
			case "ISBNKey":
				// ISBN码
				findData = Document.parse(String.format(jsonISBNKey, queryContent));
				result = ado.findWithDefaultCollection(findData);
				break;
			case "IndexKey":
				// 索书号
				findData = Document.parse(String.format(jsonIndexKey, queryContent));
				result = ado.findWithDefaultCollection(findData);
				break;
			case "SystemNumberKey":
				// 系统号
				findData = Document.parse(String.format(jsonSystemNumberKey, queryContent));
				result = ado.findWithDefaultCollection(findData);
				break;
			default:
				// 所有字段（除目录）
				findData = Document.parse(String.format(jsonOrAllKeysButNotCatalog, queryContent));
				result = ado.findWithDefaultCollection(findData);
				break;
		}
		
		// 根据排序类型来对结果进行排序
		switch(sortType) {
			case 0:
				result = result.sort(Document.parse(jsonSortType0));
				break;
			case 1:
				result = result.sort(Document.parse(jsonSortType1));
				break;
			case 2:
				result = result.sort(Document.parse(jsonSortType2));
				break;
		}
		
		// 查询到的图书总数
		int totalCount = (int)ado.getCount(findData);
		// 总页数
		int totalPage = totalCount / limitCount;
		
		
		// 查询到的图书列表
		List<Document>bookList = new ArrayList<Document>();
		for(Document document:result.skip(page*limitCount).limit(limitCount)) {
			bookList.add(document);
		}
		
		// 构造JSON
		Document JSON = new Document();
		JSON.append("bookList", bookList);
		JSON.append("totalCount", totalCount);
		JSON.append("totalPage", totalPage);
		
		view.addObject(JSON);
		
		view.setView(new MappingJackson2JsonView());
		
		return view;
	}
}
