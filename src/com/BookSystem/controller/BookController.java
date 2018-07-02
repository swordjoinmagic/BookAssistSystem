package com.BookSystem.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.bson.Document;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvcBuilderSupport;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.BookSystem.DataBaseManagement.MongoDBCommentDataBase;
import com.BookSystem.DataBaseManagement.MybatisManager;
import com.BookSystem.MybatisMapper.MybatisCommentMapper;
import com.BookSystem.javaBean.Comment;
import com.mongodb.client.FindIterable;

/*
 * ���ؾ���ĳһ����Ŀ�����
 */

@Controller
@RequestMapping("/book")
public class BookController {
	
	private String jsonISBNKey = "{'ISBN': {'$regex': '%s', '$options': 'i'}}";
	
	/**
	 * ��þ����ĳһ�����JSON�ӿ�
	 * @param id
	 * @return
	 */
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

	/**
	 * �ҵ�ĳһ���������ȫ������
	 * @param ISBN
	 * @return
	 */
	@RequestMapping(path="/comments/{ISBN}/page/{page}")
	public ModelAndView getCommentsFromISBN(@PathVariable("ISBN")String ISBN,@PathVariable(name="page") int page) {
		ModelAndView view = new ModelAndView();
		
		SqlSession session = null;
		try {
			session = MybatisManager.getSqlsessionfactory().openSession();
			MybatisCommentMapper commentMapper = session.getMapper(MybatisCommentMapper.class);
			List<Comment>comments = commentMapper.findComments(ISBN, new RowBounds(page*10,10));
			
			view.addObject("comments", comments);
			view.addObject("page",page);
			view.addObject("ISBN",ISBN);
			
			view.setView(new MappingJackson2JsonView());
		}finally {
			if(session!=null)
				session.close();
		}
		return view;
	}

	/**
	 * ����һ�����ISBN����û�id���ҵ����û����Ȿ���������������
	 * @return
	 */
	@RequestMapping(path="/comments/{ISBN}/user/{userName}/page/{page}")
	public ModelAndView getCommentsFromISBNAndUserID(
			@PathVariable("ISBN")String ISBN,
			@PathVariable("userName") String userName,
			@PathVariable("page")int page) {
		ModelAndView view = new ModelAndView();
		
		SqlSession session = null;
		try {
			session = MybatisManager.getSqlsessionfactory().openSession();
			MybatisCommentMapper mapper = session.getMapper(MybatisCommentMapper.class);
			
			List<Comment>comments = mapper.findCommentsWithISBNAndUserID(ISBN, userName, new RowBounds(page*10,10));
			
			view.addObject("comments",comments);
			view.addObject("page",page);
			view.addObject("userID",userName);
			view.addObject("ISBN",ISBN);
			
			view.setView(new MappingJackson2JsonView());
		}finally {
			if(session!=null)
				session.close();
		}
		return view;
	}
}
