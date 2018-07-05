package com.BookSystem.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.BookSystem.Decryption.MyDecryptionUtil;
import com.BookSystem.HttpUtil.HttpClientUtil;

/**
 * 用于登录的控制器
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	
	/**
	 * 用于跳转至登录界面，同时，给语音验证码设置题目以及答案
	 * @return
	 */
	@RequestMapping()
	public ModelAndView show() {
		ModelAndView view = new ModelAndView();
		view.setViewName("login");
		
		return view;
	}
	
	/**
	 * 登录检查,这里检查该账号是否可以登录学校图书馆
	 * @return
	 */
	@RequestMapping(path="/check")
	public ModelAndView loginCheck(
			@RequestParam(name="userName") String userName,
			@RequestParam(name="password") String password,
			@RequestParam(name="token") String token,
			HttpServletRequest httpServletRequest) {
		ModelAndView view = new ModelAndView();
		view.setView(new MappingJackson2JsonView());
		
		// 判断token,如果不一致，直接返回
		if(!MyDecryptionUtil.isConsistent(token)) {
			view.addObject("status",false);
			view.addObject("errorMsg","token不一致");
			return view;
		}
		
		// ToDO，这一段使用python开放的接口，判断是否可以登录学校图书馆
		String url = "http://localhost:8088/interface/login?userName=%s&password=%s";
		JSONObject result = HttpClientUtil.get(String.format(url, userName,password));
		
		// 获得请求到的JSON的状态
		boolean status = result.getBoolean("status");
		String errorMsg = result.getString("errorMsg");
		
		// 完成登录验证后，将用户名加入session
		if(status) {
			System.out.println("status的状态是:"+status+" 写入session");
			HttpSession session = httpServletRequest.getSession();
			session.setAttribute("userName", userName);
		}
		
		view.addObject("status",true);
		view.addObject("errorMsg",errorMsg);
		return view;
	}
}
