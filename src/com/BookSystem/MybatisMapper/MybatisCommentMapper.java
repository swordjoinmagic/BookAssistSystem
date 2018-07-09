package com.BookSystem.MybatisMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.BookSystem.javaBean.Comment;

public interface MybatisCommentMapper {
	/**
	 * ����ISBN�룬�ҵ�һ�����������������
	 * @param fromISBN
	 * @param rowBounds ���ڷ�ҳ
	 * @return
	 */
	public List<Comment> findComments(@Param("fromISBN") String fromISBN,RowBounds rowBounds);

	/**
	 * ����ISBN�룬UserID����һ���������ĳ���û�����������
	 * @param fromISBN
	 * @param fromUserID
	 * @param rowBounds
	 * @return
	 */
	public List<Comment> findCommentsWithISBNAndUserID(@Param("fromISBN") String fromISBN,@Param("fromUserID") String fromUserID,RowBounds rowBounds);

	/**
	 * ����ISBN���USerID����һ�������һ���û�������
	 * @param fromISBN
	 * @param fromUserID
	 */
	public void insertComment(@Param("fromISBN")String fromISBN,@Param("fromUserID")String fromUserID,@Param("comment")String comment);

	public int findCommentsTotalWithISBN(@Param("fromISBN")String fromISBN);
}
