package com.BookSystem.controller;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.bson.Document;
import org.omg.PortableServer.AdapterActivatorOperations;
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

@Controller
@RequestMapping
public class BookSearchController {
	
	// 查询字典的字符串表示
	public static String jsonOrAllKeysButNotCatalog = "{'$or':[" + 
			"{'bookName':{'$regex':'%1$s','$options':'i'}}," + 
			"{'content': {'$regex': '%1$s', '$options': 'i'}}," + 
			"{'author': {'$regex': '%1$s', '$options': 'i'}}," + 
			"{'ISBN': {'$regex': '%1$s', '$options': 'i'}}," + 
			"{'publishYear': {'$regex': '%1$s', '$options': 'i'}}," + 
			"{'index': {'$regex': '%1$s', '$options': 'i'}}," + 
			"{'publisher': {'$regex': '%1$s', '$options': 'i'}}," + 
			"{'systemNumber': {'$regex': '%1$s', '$options': 'i'}}" + 
			"]}";
	public static  String jsonCatalogKey = "{'catalog': {'$regex': '%s', '$options': 'i'}}";
	public static  String jsonBookNameKey = "{'bookName': {'$regex': '%s', '$options': 'i'}}";
	public static  String jsonBookAuthorKey = "{'author': {'$regex': '%s', '$options': 'i'}}";
	public static  String jsonBookPublisherKey = "{'publisher': {'$regex': '%s', '$options': 'i'}}";
	public static  String jsonISBNKey = "{'ISBN': {'$regex': '%s', '$options': 'i'}}";
	public static  String jsonIndexKey = "{'index': {'$regex': '%s', '$options': 'i'}}";
	public static  String jsonSystemNumberKey = "{'systemNumber':{'$regex': '%s', '$options': 'i'}}";
	public static  String jsonAllKey = "{'$or':[" + 
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
	public static  String jsonSortType0 = "{" + 
			"'publishYear':-1," + 
			"'ratingAverage':-1," + 
			"'ratingNumberRaters':-1" + 
			"}";
	public static  String jsonSortType1 = "{" + 
			"'ratingAverage':-1," + 
			"'publishYear':-1," + 
			"'ratingNumberRaters':-1" + 
			"}";
	public static  String jsonSortType2 = "{" + 
			"'ratingNumberRaters':-1," + 
			"'publishYear':-1," + 
			"'ratingAverage':-1" + 
			"}";
	

	public static  String jsonBookCollectionStatus = "{'remain':{" + 
			"'free':'正在加载'," + 
			"'Lent':'正在加载'" + 
			"}}";
	
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
	 * @param isNewBook: 本次查询是否为新书查询，如果是新书查询，那么最后result将在新书库中查询
	 * @return
	 */
	@RequestMapping(path="/search",method=RequestMethod.GET)
	public ModelAndView search(
			@RequestParam(name="page",required=false,defaultValue="0") int page,
			@RequestParam(name="sortType",required=false,defaultValue="0") int sortType,
			@RequestParam(name="searchType",required=false,defaultValue="") String searchType,
			@RequestParam(name="queryContent",required=false,defaultValue="") String queryContent,
			@RequestParam(name="extraConditionYear",required=false,defaultValue="") String extraConditionYear,
			@RequestParam(name="isNewBook",required=false,defaultValue="false")String isNewBook) {
		
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
//				result = ado.findWithDefaultCollection(findData);
				break;
			case "CatalogKey":
				// 目录
				findData = Document.parse(String.format(jsonCatalogKey, queryContent));
				break;
			case "BookNameKey":
				// 书名
				findData = Document.parse(String.format(jsonBookNameKey, queryContent));
				break;
			case "BookAuthorKey":
				// 作者
				findData = Document.parse(String.format(jsonBookAuthorKey, queryContent));
				break;
			case "BookPublisherKey":
				// 出版社
				findData = Document.parse(String.format(jsonBookPublisherKey, queryContent));
				break;
			case "ISBNKey":
				// ISBN码
				findData = Document.parse(String.format(jsonISBNKey, queryContent));
				break;
			case "IndexKey":
				// 索书号
				findData = Document.parse(String.format(jsonIndexKey, queryContent));
				break;
			case "SystemNumberKey":
				// 系统号
				findData = Document.parse(String.format(jsonSystemNumberKey, queryContent));
				break;
			default:
				// 所有字段（除目录）
				findData = Document.parse(String.format(jsonOrAllKeysButNotCatalog, queryContent));
				break;
		}
		
		// 增加额外条件
		try {
			if(!extraConditionYear.equals("")) {
				findData.append("publishYear", Integer.parseInt(extraConditionYear));
			}
		}catch(Exception e) {}
				
		if(isNewBook.equals("true")) {
			// 前往新书库查找结果
			result = ado.find(ado.getCollection("newBookData"), findData);
		}else {
			result = ado.findWithDefaultCollection(findData);
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
		
		
		Document defaultStatus = Document.parse(jsonBookCollectionStatus);
		
		for(Document book : bookList) {
			book.append("remainDataXiLi", defaultStatus);
			book.append("remainDataLiuXian", defaultStatus);
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

	@RequestMapping("/bookSearch")
	public ModelAndView bookSearch(
			HttpServletRequest httpServletRequest,
			@RequestParam(name="queryContent",required=false,defaultValue="")String extraQuery,
			@RequestParam(name="sortType",required=false,defaultValue="0")int extraSortType,
			@RequestParam(name="searchType",required=false,defaultValue="") String extraSearchType,
			@RequestParam(name="extraPage",required=false,defaultValue="0")int extraPage
			) {
		ModelAndView view = new ModelAndView();
		view.setViewName("bookSearch");
		
		HttpSession httpSession = httpServletRequest.getSession();
		
		System.out.println("ifLogin:");
		System.out.println(httpSession.getAttribute("isLogin"));
		
		view.addObject("extraQuery",extraQuery);
		view.addObject("extraPage",extraPage);
		view.addObject("extraSearchType",extraSearchType);
		view.addObject("extraSortType",extraSortType);
		return view;
	}

	@RequestMapping("/newBook")
	public ModelAndView newBookSearch() {
		ModelAndView view = new ModelAndView();
		view.setViewName("newBook");
		return view;
	}
}
