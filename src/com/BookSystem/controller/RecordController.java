package com.BookSystem.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.BookSystem.DataBaseManagement.MybatisManager;
import com.BookSystem.MybatisMapper.MybatisRecordMapper;
import com.BookSystem.MybatisMapper.MybatisSpecialKeyMapper;
import com.BookSystem.javaBean.FreeNotice;
import com.BookSystem.javaBean.HistoryRecord;
import com.BookSystem.javaBean.SpecialKey;

/**
 * ���ظ�ʽ�����ļ�¼�Ŀ�����
 *
 */
@Controller
@RequestMapping("/record")
public class RecordController {
	@RequestMapping(path="/historys",method=RequestMethod.GET)
	public ModelAndView getHistorys(
			@RequestParam("fromUserID")String fromUserID,
			@RequestParam("page") int page) {
		ModelAndView view = new ModelAndView();
		view.setView(new MappingJackson2JsonView());
		
		SqlSession session = MybatisManager.getSqlsessionfactory().openSession();
		MybatisRecordMapper recordMapper = session.getMapper(MybatisRecordMapper.class);
		
		List<HistoryRecord>list = recordMapper.findHistoryRecords(fromUserID, new RowBounds(page*10,10));
		
		view.addObject("historyList",list);
		
		return view;
	}
	
	/**
	 * ��ùݲؿ���֪ͨ
	 * @return
	 */
	@RequestMapping(path="/freeNotices",method=RequestMethod.GET)
	public ModelAndView getCollectionFreeNotices(
			@RequestParam("fromUserID")String fromUserID,
			@RequestParam("page")int page) {
		ModelAndView view = new ModelAndView();
		view.setView(new MappingJackson2JsonView());
		
		SqlSession session = MybatisManager.getSqlsessionfactory().openSession();
		MybatisRecordMapper recordMapper = session.getMapper(MybatisRecordMapper.class);
		
		// ������ݼ�
		List<FreeNotice>list = recordMapper.findCollectionFreeNotices(fromUserID, new RowBounds(page*10,10));
		session.close();
		view.addObject("freeNoticeList",list);
		
		return view;
	}

	/**
	 * ��õ�ǰsession�е�UserName
	 * @return
	 */
	@RequestMapping("/getName")
	public ModelAndView getUserName(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		view.setView(new MappingJackson2JsonView());
		
		HttpSession session = request.getSession();
		
		String username = (String) session.getAttribute("userName");
		
		view.addObject("userName",username);
		
		return view;
	}

	@RequestMapping(path="getSpecialKeys")
	public ModelAndView getSpecialKeys(HttpServletRequest request) {
		
		// ����û�ID
		ModelAndView view = new ModelAndView();
		view.setView(new MappingJackson2JsonView());
		
		HttpSession session = request.getSession();
		
		String username = (String) session.getAttribute("userName");
		
		if(username==null) {
			JSONObject jsonObject = new JSONObject();
			view.addObject("stauts",false);
			view.addObject("errorMsg","�㻹û�е�¼");
			return view;
		}
		
		SqlSession sqlSession = MybatisManager.getSqlsessionfactory().openSession();
		
		MybatisSpecialKeyMapper specialKeyMapper = sqlSession.getMapper(MybatisSpecialKeyMapper.class);
		
		List<SpecialKey>specialKeys = specialKeyMapper.findSpecialKeysWithUserID(username);
		
		sqlSession.close();
		
		view.addObject("status",true);
		view.addObject("errorMsg","");
		view.addObject("specialKeys",specialKeys);
		return view;
		
	}
}
