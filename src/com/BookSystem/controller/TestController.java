package com.BookSystem.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.ServletContextAttributeExporter;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.BookSystem.ApplicationContextUtil.ContextUtil;
import com.BookSystem.DataBaseManagement.MongoDBCommentDataBase;
import com.BookSystem.DataBaseManagement.MongoDBUtil;
import com.BookSystem.DataBaseManagement.MybatisManager;
import com.BookSystem.HttpUtil.HttpClientUtil;
import com.BookSystem.MailManager.EmailManager;
import com.BookSystem.MybatisMapper.MybatisQuestionMapper;
import com.BookSystem.MybatisMapper.MybatisSpecialKeyMapper;
import com.BookSystem.MybatisMapper.MybatisUserMapper;
import com.BookSystem.javaBean.Question;
import com.BookSystem.javaBean.SpecialKey;
import com.BookSystem.javaBean.User;
import com.mongodb.client.FindIterable;

import test.DeadlockAvoidance;
import test.Source;

@Controller
@RequestMapping("/test")
public class TestController {
	@RequestMapping(path="/test",method=RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("hi","hello world");
		modelAndView.setViewName("test");
		return modelAndView;
	}
	@RequestMapping(path="/test2")
	public ModelAndView showTest2() {
		ModelAndView view = new ModelAndView();
		view.setView(new MappingJackson2JsonView());
		
//		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
//		ServletContext servletContext = webApplicationContext.getServletContext();
//		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		
		System.out.println(this.getClass().getClassLoader().getResource("/WebContent/readme.md"));
		
		ApplicationContext context = ContextUtil.getApplicationContext();
		view.addObject(context.getBean(Source.class));
		
		return view;
	}
	@RequestMapping("test3")
	public ModelAndView test3() {
		ModelAndView view = new ModelAndView();
		view.setViewName("bookDetail");
		return view;
	}
	
	@RequestMapping("test4")
	public ModelAndView test4() {
		ModelAndView view = new ModelAndView();
		view.setViewName("osExam");
		
		DeadlockAvoidance deadlockAvoidance = new DeadlockAvoidance(5, 3);
		
		deadlockAvoidance.waitInput();
		
		view.addObject("available",deadlockAvoidance.available);
		view.addObject("allocation",deadlockAvoidance.allocation);
		view.addObject("m",deadlockAvoidance.m);
		view.addObject("n",deadlockAvoidance.n);
		view.addObject("need",deadlockAvoidance.need);
		view.addObject("max",deadlockAvoidance.max);
		
		
		
		return view;
	}
	
	@RequestMapping("/forceBorrow")
	public ModelAndView forceBorrow() {
		// 第一步，找到所有自动续借的用户
		SqlSession session = MybatisManager.getSqlsessionfactory().openSession();
		MybatisUserMapper userMapper = session.getMapper(MybatisUserMapper.class);
		
		List<User>users = null;
		try {
			users = userMapper.findUsersByEnableAutoBorrow(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(users);
		
		if(users!=null) {
			for(User user:users) {
				String userEmail = user.getEmail();
				
				// 请求自动续借的接口
				JSONObject jsonObject = HttpClientUtil.get("http://localhost:8088/interface/autoBorrow?userID="+user.getUserName());
				
				JSONArray success = (JSONArray) jsonObject.get("successBooks");
				JSONArray fails =  (JSONArray) jsonObject.get("failBooks");
				
				System.out.println(success);
				System.out.println(fails);
				
				EmailManager.getEmailManager().sendAutoBorrowTipsEmail(userEmail, success, fails);
			}
		}
		
		ModelAndView view = new ModelAndView();
		view.setView(new MappingJackson2JsonView());
		return view;
	}
	
	@RequestMapping("test5")
	public ModelAndView test5() {
		ModelAndView view = new ModelAndView();
		view.setView(new MappingJackson2JsonView());
		
		SqlSession session = MybatisManager.getSqlsessionfactory().openSession();
		
		MybatisQuestionMapper questionMapper = session.getMapper(MybatisQuestionMapper.class);
		
		Question question = questionMapper.getRandomQuestion();
		
		view.addObject(question);
		
		return view;
	}
	
	@RequestMapping("/autonewBook")
	public ModelAndView autoNewBookTest(@RequestParam(name="fromUserID")String fromUserID) throws Exception {
		ModelAndView view = new ModelAndView();
		view.setView(new MappingJackson2JsonView());
		
		// 第一步，找到该用户的所有特别关注标签
		SqlSession sqlSession = MybatisManager.getSqlsessionfactory().openSession();
		MybatisUserMapper userMapper = sqlSession.getMapper(MybatisUserMapper.class);
		MybatisSpecialKeyMapper specialKeyMapper = sqlSession.getMapper(MybatisSpecialKeyMapper.class);
		
		User user = userMapper.selectUserByUserID(fromUserID);
		List<SpecialKey>specialKeys = specialKeyMapper.findSpecialKeysWithUserID(fromUserID);
		
		// 从新书集中找特别关注标签中关注的书籍
		MongoDBCommentDataBase mongoDBCommentDataBase = MongoDBCommentDataBase.getDao();
		
		Document newBookJson = new Document();
		
		Document findData;		// 查询字典
		
		// 初始化查询结果
		FindIterable<Document>result = null;
		
		for(SpecialKey specialKey:specialKeys) {
			String queryContent = specialKey.getSpecialKey();
			String keyType = specialKey.getKeyType();
			
			// 根据searchType构造查询字典
			switch(keyType) {
				case "AllKey":
					// 所有字段
					findData = Document.parse(String.format(BookSearchController.jsonAllKey, queryContent));
					break;
				case "CatalogKey":
					// 目录
					findData = Document.parse(String.format(BookSearchController.jsonCatalogKey, queryContent));
					break;
				case "BookNameKey":
					// 书名
					findData = Document.parse(String.format(BookSearchController.jsonBookNameKey, queryContent));
					break;
				case "BookAuthorKey":
					// 作者
					findData = Document.parse(String.format(BookSearchController.jsonBookAuthorKey, queryContent));
					break;
				case "BookPublisherKey":
					// 出版社
					findData = Document.parse(String.format(BookSearchController.jsonBookPublisherKey, queryContent));
					break;
				case "ISBNKey":
					// ISBN码
					findData = Document.parse(String.format(BookSearchController.jsonISBNKey, queryContent));
					break;
				case "IndexKey":
					// 索书号
					findData = Document.parse(String.format(BookSearchController.jsonIndexKey, queryContent));
					break;
				case "SystemNumberKey":
					// 系统号
					findData = Document.parse(String.format(BookSearchController.jsonSystemNumberKey, queryContent));
					break;
				default:
					// 所有字段（除目录）
					findData = Document.parse(String.format(BookSearchController.jsonOrAllKeysButNotCatalog, queryContent));
					break;
			}
			result = mongoDBCommentDataBase.find(mongoDBCommentDataBase.getCollection("newBookData"), findData);
			List<Document>lists = new ArrayList<Document>();
			for(Document d:result) {
				lists.add(d);
			}
			newBookJson.append(queryContent+"("+keyType+")", lists);
		}
		
		sqlSession.close();
		
		System.out.println("读者的新书速递书中有这些内容:");
		System.out.println(newBookJson.toJson());
		
		EmailManager.getEmailManager().sendnewBookTipsEmail(user.getEmail(), newBookJson);
		
		return view;
	}
}
