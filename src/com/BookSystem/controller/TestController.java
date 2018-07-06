package com.BookSystem.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.ServletContextAttributeExporter;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.BookSystem.ApplicationContextUtil.ContextUtil;
import com.BookSystem.DataBaseManagement.MybatisManager;
import com.BookSystem.MybatisMapper.MybatisQuestionMapper;
import com.BookSystem.javaBean.Question;

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
}
