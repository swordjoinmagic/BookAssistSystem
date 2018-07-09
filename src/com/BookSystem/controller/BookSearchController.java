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
	
	// ��ѯ�ֵ���ַ�����ʾ
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
	
	// �����ѯ�ֵ��ַ�����ʾ
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
			"'free':'���ڼ���'," + 
			"'Lent':'���ڼ���'" + 
			"}}";
	
	// һҳ�ж���������
	private int limitCount = 10;
	
	/**
	 * 
	 * @param page ��ǰҳ�� 
	 * @param sortType ��ǰ�������ͣ�
	 * 0����-����-����������
	 * 1������-��-����������
	 * 2����������-��-����
	 * @param searchType ��ǰ�������ֶ�,��Ϊ�������ֶΡ�Ŀ¼�����ߡ�������ISBN�ŵȵ�
	 * 					 �ֱ��ǣ�
	 * 					"AllKeyButNotCatalog" - �����ֶΣ���Ŀ¼��
	 * 					"AllKey" 			  - �����ֶ�
	 * 					"CatalogKey"		  - Ŀ¼
	 * 					"BookNameKey"		  - ����
	 * 					"BookAuthorKey"		  - ����
	 * 					"BookPublisherKey"	  - ������
	 * 					"ISBNKey"			  - ISBN
	 * 					"IndexKey"			  - �����
	 * 					"SystemNumberKey"	  - ϵͳ��
	 * @param queryContent: ��ѯ������
	 * @param isNewBook: ���β�ѯ�Ƿ�Ϊ�����ѯ������������ѯ����ô���result����������в�ѯ
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
		
		// ������ͼ-ģ��
		ModelAndView view = new ModelAndView();
		
		// �����ݿ���ȡ����
		MongoDBCommentDataBase ado = MongoDBCommentDataBase.getDao();
		
		// ��ʼ����ѯ���
		FindIterable<Document>result = null;
		
		// ����sortType��searchType�����ѯDocument
		Document findData;		// ��ѯ�ֵ�
		
		// ����searchType�����ѯ�ֵ�
		switch(searchType) {
			case "AllKey":
				// �����ֶ�
				findData = Document.parse(String.format(jsonAllKey, queryContent));
//				result = ado.findWithDefaultCollection(findData);
				break;
			case "CatalogKey":
				// Ŀ¼
				findData = Document.parse(String.format(jsonCatalogKey, queryContent));
				break;
			case "BookNameKey":
				// ����
				findData = Document.parse(String.format(jsonBookNameKey, queryContent));
				break;
			case "BookAuthorKey":
				// ����
				findData = Document.parse(String.format(jsonBookAuthorKey, queryContent));
				break;
			case "BookPublisherKey":
				// ������
				findData = Document.parse(String.format(jsonBookPublisherKey, queryContent));
				break;
			case "ISBNKey":
				// ISBN��
				findData = Document.parse(String.format(jsonISBNKey, queryContent));
				break;
			case "IndexKey":
				// �����
				findData = Document.parse(String.format(jsonIndexKey, queryContent));
				break;
			case "SystemNumberKey":
				// ϵͳ��
				findData = Document.parse(String.format(jsonSystemNumberKey, queryContent));
				break;
			default:
				// �����ֶΣ���Ŀ¼��
				findData = Document.parse(String.format(jsonOrAllKeysButNotCatalog, queryContent));
				break;
		}
		
		// ���Ӷ�������
		try {
			if(!extraConditionYear.equals("")) {
				findData.append("publishYear", Integer.parseInt(extraConditionYear));
			}
		}catch(Exception e) {}
				
		if(isNewBook.equals("true")) {
			// ǰ���������ҽ��
			result = ado.find(ado.getCollection("newBookData"), findData);
		}else {
			result = ado.findWithDefaultCollection(findData);
		}
			
		
		
		// ���������������Խ����������
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
		
		// ��ѯ����ͼ������
		int totalCount = (int)ado.getCount(findData);
		// ��ҳ��
		int totalPage = totalCount / limitCount;
		
		
		// ��ѯ����ͼ���б�
		List<Document>bookList = new ArrayList<Document>();
		for(Document document:result.skip(page*limitCount).limit(limitCount)) {
			bookList.add(document);
		}
		
		
		Document defaultStatus = Document.parse(jsonBookCollectionStatus);
		
		for(Document book : bookList) {
			book.append("remainDataXiLi", defaultStatus);
			book.append("remainDataLiuXian", defaultStatus);
		}
		
		// ����JSON
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
