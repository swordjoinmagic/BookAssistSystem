package com.BookSystem.controller;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvcBuilderSupport;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.BookSystem.DataBaseManagement.MongoDBCommentDataBase;
import com.mongodb.client.FindIterable;

/*
 * ���ؾ���ĳһ����Ŀ�����
 */

@Controller
@RequestMapping("/book")
public class BookController {
	
	private String jsonISBNKey = "{'ISBN': {'$regex': '%s', '$options': 'i'}}";
	
	@RequestMapping(path="/{id}",method=RequestMethod.GET)
	public ModelAndView getBook(@PathVariable("id")String id) {
		ModelAndView view = new ModelAndView();
		
		// ������ݿ�ʵ��
		MongoDBCommentDataBase ado = MongoDBCommentDataBase.getDao();
		
		// ͨ��ISBN�Ų�����
		Document findData = Document.parse(String.format(jsonISBNKey, id));
		
		System.out.println(findData.toJson());
		
		FindIterable<Document>result = ado.findWithDefaultCollection(findData);
		
		List<Document>books = new ArrayList<Document>();
		for(Document document:result) {
			books.add(document);
		}
		
		view.addObject(books);
		view.setView(new MappingJackson2JsonView());
		
		return view;
	}
}
