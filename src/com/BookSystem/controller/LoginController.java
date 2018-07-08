package com.BookSystem.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.BookSystem.DataBaseManagement.MybatisManager;
import com.BookSystem.Decryption.MyDecryptionUtil;
import com.BookSystem.HttpUtil.HttpClientUtil;
import com.BookSystem.MybatisMapper.MybatisQuestionMapper;
import com.BookSystem.javaBean.Question;

import sun.misc.BASE64Encoder;

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
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping()
	public ModelAndView show(
			HttpServletRequest httpServletRequest,
			@RequestParam(name="username",required=false,defaultValue="") String username,
			@RequestParam(name="password",required=false,defaultValue="") String password
			) throws UnsupportedEncodingException {
		ModelAndView view = new ModelAndView();
		view.setViewName("login");
		
		SqlSession session = MybatisManager.getSqlsessionfactory().openSession();
		
		MybatisQuestionMapper questionMapper = session.getMapper(MybatisQuestionMapper.class);
		
		Question question = questionMapper.getRandomQuestion();
		
		httpServletRequest.getSession().setAttribute("answer", question.getAnswer());
		
		String encodeQuestion = new BASE64Encoder().encode(question.getQuestion().getBytes("UTF-8"));
		
		encodeQuestion = URLEncoder.encode(encodeQuestion);
		
		view.addObject("qustionContent",encodeQuestion);
		view.addObject("userName",username);
		view.addObject("password",password);
		return view;
	}
	
	/**
	 * 登录检查,这里检查该账号是否可以登录学校图书馆
	 * @return
	 */
	@RequestMapping(path="/check")
	public ModelAndView loginCheck(
			@RequestParam(name="userName",required=false,defaultValue="") String userName,
			@RequestParam(name="password",required=false,defaultValue="") String password,
			@RequestParam(name="token",required=false,defaultValue="") String token,
			@RequestParam(name="verificationCode",required=false,defaultValue="")String verificationCode,
			HttpServletRequest httpServletRequest) {
		ModelAndView view = new ModelAndView();
		view.setView(new MappingJackson2JsonView());
		
		// 判断token,如果不一致，直接返回
		if(!MyDecryptionUtil.isConsistent(token)) {
			view.addObject("status",false);
			view.addObject("errorMsg","token不一致");
			return view;
		}
		
		// 判断验证码是否正确
		String answer="";
		try {
			answer = (String) httpServletRequest.getSession().getAttribute("answer");
		}catch(Exception e) {
			e.printStackTrace();
			view.addObject("status",false);
			view.addObject("errorMsg","没有答案");
			return view;
		}
		System.out.println("提交的答案是:"+verificationCode+" 验证码是:"+answer);
		if(!verificationCode.equals(answer)) {
			String errorMsg = "验证码不正确";
			view.addObject("status",false);
			view.addObject("errorMsg",errorMsg);
			return view;
		}
		
		// ToDO，这一段使用python开放的接口，判断是否可以登录学校图书馆
		String url = "http://localhost:8088/interface/login?userName=%s&password=%s";
		
		
		JSONObject result = HttpClientUtil.get(String.format(url, userName,password));
		
		System.out.println(String.format(url, userName,password));
		System.out.println("result:"+result.toString());
		
		boolean status;
		String errorMsg;
		// 获得请求到的JSON的状态
		try {
			status = result.getBoolean("status");
			errorMsg = result.getString("errorMsg");
		}catch(Exception e) {
			e.printStackTrace();
			status = false;
			errorMsg = "服务器内部错误";

		}
		// 完成登录验证后，将用户名加入session
		if(status) {
			System.out.println("status的状态是:"+status+" 写入session");
			HttpSession session = httpServletRequest.getSession();
			session.setAttribute("userName", userName);
			session.setAttribute("isLogin", true);
		}
		
		view.addObject("status",status);
		view.addObject("errorMsg",errorMsg);
		return view;
	}

	/**
	 * 退出登录
	 * @return
	 */
	@RequestMapping(path="/quit")
	public ModelAndView quit(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		view.setViewName("bookSearch");
		
		HttpSession session = request.getSession();
		session.setAttribute("isLogin", false);
		session.setAttribute("userName", "");
		
		return view;
	}
}
