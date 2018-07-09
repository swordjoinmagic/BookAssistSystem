package com.BookSystem.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.BookSystem.DataBaseManagement.MybatisManager;
import com.BookSystem.MybatisMapper.MybatisSpecialKeyMapper;
import com.BookSystem.MybatisMapper.MybatisUserMapper;
import com.BookSystem.javaBean.SpecialKey;
import com.BookSystem.javaBean.User;

/**
 * 
 * 用于基本设置的控制器
 *
 */
@Controller
@RequestMapping("/setup")
public class SetupController {
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView showSetupView(
			HttpServletRequest request,
			@RequestParam(name="actived",required=false,defaultValue="baseSetup")String actived) throws Exception {
		
		HttpSession httpSession = request.getSession();
		String fromUserID;
		try {
			fromUserID = (String) httpSession.getAttribute("userName");
		}catch(Exception e) {
			e.printStackTrace();
			fromUserID = "";
		}
		
		ModelAndView view = new ModelAndView();
		view.setViewName("setup");
		
		// 获得mybatis的session
		SqlSession session = MybatisManager.getSqlsessionfactory().openSession();
		
		// 获得接口
		MybatisUserMapper userMapper = session.getMapper(MybatisUserMapper.class);
		MybatisSpecialKeyMapper specialKeyMapper = session.getMapper(MybatisSpecialKeyMapper.class);
		
		// 根据UserID，得到用户数据
		User user = userMapper.selectUserByUserID(fromUserID);
		
		List<SpecialKey>specialKeys = specialKeyMapper.findSpecialKeysWithUserID(fromUserID);
		
		System.out.println(user);
		
		session.close();
		view.addObject("user",user);
		view.addObject("specialKeys",specialKeys);
		
		// 判断用户是否开启自动续借和新书速递
		view.addObject("isAutoBorrow",user.getIsEnableAutoBorrow()==1?true:false);
		view.addObject("isAutoNewBook",user.getIsEnableAutoNewBook()==1?true:false);
		view.addObject("setupActived",actived);
		
		return view;
	}

	@RequestMapping("/email")
	public ModelAndView setupEmail(
			@RequestParam(name="email")String email,
			HttpServletRequest request
			) {
		HttpSession httpSession = request.getSession();
		String userName = "";
		try {
			userName = (String) httpSession.getAttribute("userName");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		ModelAndView view = new ModelAndView();
		view.setViewName("redirect:/setup");
		
		SqlSession session = MybatisManager.getSqlsessionfactory().openSession();
		MybatisUserMapper userMapper = session.getMapper(MybatisUserMapper.class);
		
		System.out.println("email是："+email+" userName:"+userName);
		
		// 进行更新
		try {
			userMapper.updateUserEmail(email, userName);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return view;
		
	}

	// AJAX接口
	@RequestMapping(path="/addSpeicalKey")
	public ModelAndView addSpeicalKey(
			HttpServletRequest request,
			@RequestParam("key")String key,
			@RequestParam("KeyCode")String KeyCode) {
		// 获得session
		HttpSession httpSession = request.getSession();
		String userName = "";
		try {
			userName = (String) httpSession.getAttribute("userName");
		}catch(Exception e){e.printStackTrace();}
		
		// 构造ModelAndView
		ModelAndView view = new ModelAndView();
		view.setViewName("redirect:/setup?actived=specialKey");
		
		SqlSession session = MybatisManager.getSqlsessionfactory().openSession();
		MybatisSpecialKeyMapper specialKeyMapper = session.getMapper(MybatisSpecialKeyMapper.class);
		
		specialKeyMapper.insertSpecialkeyWithUserID(userName, key, KeyCode);
		session.commit();
		session.close();
		
		
		return view;
	}
}
