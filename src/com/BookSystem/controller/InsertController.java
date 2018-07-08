package com.BookSystem.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.ibatis.jdbc.Null;
import org.apache.ibatis.session.SqlSession;
import org.bson.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.BookSystem.DataBaseManagement.MybatisManager;
import com.BookSystem.MybatisMapper.MybatisCommentMapper;


/**
 * 
 * �����������ݿ���������Ŀ�����
 *
 */
@Controller
@RequestMapping("/insert")
public class InsertController {
		
	/**
	 * ����һ�����ISBN�룬�û�ID��������������ݣ�Ϊһ�����������,
	 * ����һ��JSON��������ʽ���£�
	 * {
	 * 		status:  true/false,
	 * 		errorMsg: "xxxxxxxxxxx"
	 * }
	 * �����Ƿ�ִ�гɹ���״̬������ɹ�������true�����ʧ�ܣ�����false��ͬʱ
	 * errorMsg�л���ʧ�ܵ���Ϣ
	 * @param ISBN
	 * @param fromUserID
	 * @param comment
	 * @return
	 */
	@RequestMapping(path="/comments")
	public ModelAndView insertComments(@RequestParam("ISBN")String ISBN,@RequestParam("fromUserID")String fromUserID,@RequestParam("comment")String comment) {
		ModelAndView view = new ModelAndView();
		
		// ����Mybatisӳ����
		SqlSession session = MybatisManager.getSqlsessionfactory().openSession();
		MybatisCommentMapper commentMapper = session.getMapper(MybatisCommentMapper.class);
		
		commentMapper.insertComment(ISBN, fromUserID, comment);
		session.commit();
				
		session.close();
		view.addObject("status",true);
		view.addObject("errorMsg",null);
		
		view.setView(new MappingJackson2JsonView());
		return view;
	}
}
