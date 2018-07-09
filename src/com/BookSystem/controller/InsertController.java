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
import com.BookSystem.Decryption.MyDecryptionUtil;
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
	public ModelAndView insertComments(
			HttpServletRequest request,
			@RequestParam("ISBN")String ISBN,
			@RequestParam("comment")String comment,
			@RequestParam(name="token",required=false,defaultValue="") String token) {
		ModelAndView view = new ModelAndView();
		view.setView(new MappingJackson2JsonView());

		// �ж�token,�����һ�£�ֱ�ӷ���
		if(!MyDecryptionUtil.isConsistent(token)) {
			view.addObject("status",false);
			view.addObject("errorMsg","token��һ��");
			return view;
		}
		
		HttpSession httpSession = request.getSession();
		
		String fromUserID = (String) httpSession.getAttribute("userName");
		
		if(fromUserID==null) {
			JSONObject jsonObject = new JSONObject();
			view.addObject("stauts",false);
			view.addObject("errorMsg","�㻹û�е�¼");
			return view;
		}
		
		// ����Mybatisӳ����
		SqlSession session = MybatisManager.getSqlsessionfactory().openSession();
		MybatisCommentMapper commentMapper = session.getMapper(MybatisCommentMapper.class);
		
		commentMapper.insertComment(ISBN, fromUserID, comment);
		session.commit();
		
		int totalCount = commentMapper.findCommentsTotalWithISBN(ISBN);
				
		session.close();
		view.addObject("status",true);
		view.addObject("errorMsg",null);
		view.addObject("totalCount",totalCount);
		
		return view;
	}
}
