package com.BookSystem.MybatisMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.BookSystem.javaBean.SpecialKey;

public interface MybatisSpecialKeyMapper {
	/**
	 * 根据用户ID，查找某个用户下的所有特别关注标签
	 * @param fromUserID
	 * @return
	 */
	public List<SpecialKey> findSpecialKeysWithUserID(@Param("fromUserID") String fromUserID);

	/*
	 * 根据用户ID，为用户插入特别关注标签
	 */
	public void insertSpecialkeyWithUserID(@Param("fromUserID") String fromUserID,@Param("specialKey")String specialKey,@Param("keyType")String keyType);
}
