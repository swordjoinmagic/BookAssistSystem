package com.BookSystem.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.BookSystem.DataBaseManagement.MybatisManager;
import com.BookSystem.MybatisMapper.MybatisSpecialKeyMapper;
import com.BookSystem.MybatisMapper.MybatisUserMapper;
import com.BookSystem.javaBean.SpecialKey;
import com.BookSystem.javaBean.User;

/**
 * 
 * ���ڻ������õĿ�����
 *
 */
@Controller
@RequestMapping("/setup")
public class SetupController {
	@RequestMapping(path="/{fromUserID}",method=RequestMethod.GET)
	public ModelAndView showSetupView(@PathVariable("fromUserID")String fromUserID) throws Exception {
		ModelAndView view = new ModelAndView();
		view.setViewName("setup");
		
		// ���mybatis��session
		SqlSession session = MybatisManager.getSqlsessionfactory().openSession();
		
		// ��ýӿ�
		MybatisUserMapper userMapper = session.getMapper(MybatisUserMapper.class);
		MybatisSpecialKeyMapper specialKeyMapper = session.getMapper(MybatisSpecialKeyMapper.class);
		
		// ����UserID���õ��û�����
		User user = userMapper.selectUserByUserID(fromUserID);
		
		List<SpecialKey>specialKeys = specialKeyMapper.findSpecialKeysWithUserID(fromUserID);
		
		System.out.println(user);
		
		view.addObject("user",user);
		view.addObject("specialKeys",specialKeys);
		
		return view;
	}
}
