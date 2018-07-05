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
 * ���ڵ�¼�Ŀ�����
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	
	/**
	 * ������ת����¼���棬ͬʱ����������֤��������Ŀ�Լ���
	 * @return
	 */
	@RequestMapping()
	public ModelAndView show() {
		ModelAndView view = new ModelAndView();
		view.setViewName("login");
		
		return view;
	}
	
	/**
	 * ��¼���,��������˺��Ƿ���Ե�¼ѧУͼ���
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
		
		// �ж�token,�����һ�£�ֱ�ӷ���
		if(!MyDecryptionUtil.isConsistent(token)) {
			view.addObject("status",false);
			view.addObject("errorMsg","token��һ��");
			return view;
		}
		
		// ToDO����һ��ʹ��python���ŵĽӿڣ��ж��Ƿ���Ե�¼ѧУͼ���
		String url = "http://localhost:8088/interface/login?userName=%s&password=%s";
		JSONObject result = HttpClientUtil.get(String.format(url, userName,password));
		
		// ������󵽵�JSON��״̬
		boolean status = result.getBoolean("status");
		String errorMsg = result.getString("errorMsg");
		
		// ��ɵ�¼��֤�󣬽��û�������session
		if(status) {
			System.out.println("status��״̬��:"+status+" д��session");
			HttpSession session = httpServletRequest.getSession();
			session.setAttribute("userName", userName);
		}
		
		view.addObject("status",true);
		view.addObject("errorMsg",errorMsg);
		return view;
	}
}
