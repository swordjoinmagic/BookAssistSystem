package com.BookSystem.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.BookSystem.Decryption.MyDecryptionUtil;

/**
 * ���ڵ�¼�Ŀ�����
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	/**
	 * ��¼���
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)
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
		
		
		// ��ɵ�¼��֤�󣬽��û�������session
		HttpSession session = httpServletRequest.getSession();
		session.setAttribute("userName", userName);
		view.addObject("status",true);
		view.addObject("errorMsg",null);
		
		return view;
	}
}
