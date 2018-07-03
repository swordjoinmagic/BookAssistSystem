package com.BookSystem.MybatisMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.BookSystem.javaBean.FreeNotice;
import com.BookSystem.javaBean.HistoryRecord;

/**
 * ���ظ�ʽ�����ļ�¼��ӳ����
 * @author Administrator
 *
 */
public interface MybatisRecordMapper {
	/**
	 * �����û�ID���ҵ������û���������ʷ��¼
	 * @param fromUserID
	 * @param rowBounds
	 * @return
	 */
	public List<HistoryRecord> findHistoryRecords(String fromUserID,RowBounds rowBounds);

	/**
	 * �����û�ID���ҵ������û��Ĺݲؿ���֪ͨ��¼
	 * @param fromUserID
	 * @param rowBounds
	 * @return
	 */
	public List<FreeNotice> findCollectionFreeNotices(String fromUserID,RowBounds rowBounds);
}
