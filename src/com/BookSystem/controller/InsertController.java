package com.BookSystem.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.jdbc.Null;
import org.apache.ibatis.session.SqlSession;
import org.bson.Document;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.BookSystem.DataBaseManagement.MybatisManager;
import com.BookSystem.MybatisMapper.MybatisCommentMapper;


/**
 * 
 * 用来管理数据库插入等事务的控制器
 *
 */
@Controller
@RequestMapping("/insert")
public class InsertController {
		
	/**
	 * 根据一本书的ISBN码，用户ID，发表的评论内容，为一本书插入评论,
	 * 返回一个JSON对象，其形式如下：
	 * {
	 * 		status:  true/false,
	 * 		errorMsg: "xxxxxxxxxxx"
	 * }
	 * 返回是否执行成功的状态，如果成功，返回true，如果失败，返回false，同时
	 * errorMsg中会有失败的信息
	 * @param ISBN
	 * @param fromUserID
	 * @param comment
	 * @return
	 */
	@RequestMapping(path="/comments")
	public ModelAndView insertComments(HttpServletRequest request,@RequestParam("ISBN")String ISBN,@RequestParam("comment")String comment) {
		ModelAndView view = new ModelAndView();
		
		HttpSession httpSession = request.getSession();
		
		String fromUserID = (String) httpSession.getAttribute("userName");
		
		if(fromUserID==null) {
			JSONObject jsonObject = new JSONObject();
			view.addObject("stauts",false);
			view.addObject("errorMsg","你还没有登录");
			return view;
		}
		
		// 构造Mybatis映射器
		SqlSession session = MybatisManager.getSqlsessionfactory().openSession();
		MybatisCommentMapper commentMapper = session.getMapper(MybatisCommentMapper.class);
		
		commentMapper.insertComment(ISBN, fromUserID, comment);
		session.commit();
		
		int totalCount = commentMapper.findCommentsTotalWithISBN(ISBN);
				
		session.close();
		view.addObject("status",true);
		view.addObject("errorMsg",null);
		view.addObject("totalCount",totalCount);
		
		view.setView(new MappingJackson2JsonView());
		return view;
	}
}
