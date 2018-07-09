package com.BookSystem.MybatisMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.BookSystem.javaBean.Comment;

public interface MybatisCommentMapper {
	/**
	 * 根据ISBN码，找到一本书下面的所有评论
	 * @param fromISBN
	 * @param rowBounds 用于分页
	 * @return
	 */
	public List<Comment> findComments(@Param("fromISBN") String fromISBN,RowBounds rowBounds);

	/**
	 * 根据ISBN码，UserID，找一本书下面的某个用户的所有评论
	 * @param fromISBN
	 * @param fromUserID
	 * @param rowBounds
	 * @return
	 */
	public List<Comment> findCommentsWithISBNAndUserID(@Param("fromISBN") String fromISBN,@Param("fromUserID") String fromUserID,RowBounds rowBounds);

	/**
	 * 根据ISBN码和USerID，对一本书插入一个用户的评论
	 * @param fromISBN
	 * @param fromUserID
	 */
	public void insertComment(@Param("fromISBN")String fromISBN,@Param("fromUserID")String fromUserID,@Param("comment")String comment);

	public int findCommentsTotalWithISBN(@Param("fromISBN")String fromISBN);
}
