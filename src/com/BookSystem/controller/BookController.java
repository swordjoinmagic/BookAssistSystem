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
 * 返回具体某一本书的控制器
 */

@Controller
@RequestMapping("/book")
public class BookController {
	private String jsonISBNKey = "{'ISBN': {'$regex': '%s', '$options': 'i'}}";
	/**
	 * 获得具体的某一本书的JSON接口
	 * @param id
	 * @return
	 */
	@RequestMapping(path="/{id}",method=RequestMethod.GET)
	public ModelAndView getBook(@PathVariable("id")String id) {
		ModelAndView view = new ModelAndView();
		
		// 获得数据库实例
		MongoDBCommentDataBase ado = MongoDBCommentDataBase.getDao();
		
		// 通过ISBN号查找书
		Document findData = Document.parse(String.format(jsonISBNKey, id));
				
		FindIterable<Document>result = ado.findWithDefaultCollection(findData);
		
		List<Document>books = new ArrayList<Document>();
		for(Document document:result) {
			books.add(document);
		}
		
		try {
			for(String key : books.get(0).keySet()) {
				// 缩短书名
				if(key.equals("bookName")) {
					String bookName = books.get(0).getString(key);
					books.get(0).put(key, bookName.split("/|=")[0]);
				}
					
				view.addObject(key,books.get(0).get(key));
			}
			String content = books.get(0).getString("content");
			String catalog = books.get(0).getString("catalog");
			
			// 添加smallContent和smallCatalog
			view.addObject("smallContent",content.substring(0, content.length()/5));
			view.addObject("smallCatalog",catalog.substring(0, catalog.length()/3));
			 
			view.setViewName("bookDetail");
		}catch(Exception e) {
			e.printStackTrace();
			view.setViewName("index"); 
		}
		return view;
	}

	/**
	 * 找到某一本书下面的全部评论
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
			List<Comment>comments = commentMapper.findComments(ISBN, new RowBounds(page*5,5));
			
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
	 * 根据一本书的ISBN码和用户id，找到该用户在这本书下面的所有评论
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
