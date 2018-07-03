package com.BookSystem.MybatisMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.BookSystem.javaBean.FreeNotice;
import com.BookSystem.javaBean.HistoryRecord;

/**
 * 返回各式各样的记录的映射器
 * @author Administrator
 *
 */
public interface MybatisRecordMapper {
	/**
	 * 根据用户ID，找到该名用户的所有历史记录
	 * @param fromUserID
	 * @param rowBounds
	 * @return
	 */
	public List<HistoryRecord> findHistoryRecords(String fromUserID,RowBounds rowBounds);

	/**
	 * 根据用户ID，找到该名用户的馆藏空闲通知记录
	 * @param fromUserID
	 * @param rowBounds
	 * @return
	 */
	public List<FreeNotice> findCollectionFreeNotices(String fromUserID,RowBounds rowBounds);
}
