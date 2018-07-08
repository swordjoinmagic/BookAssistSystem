package com.BookSystem.MybatisMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.BookSystem.javaBean.SpecialKey;

public interface MybatisSpecialKeyMapper {
	/**
	 * �����û�ID������ĳ���û��µ������ر��ע��ǩ
	 * @param fromUserID
	 * @return
	 */
	public List<SpecialKey> findSpecialKeysWithUserID(@Param("fromUserID") String fromUserID);

	/*
	 * �����û�ID��Ϊ�û������ر��ע��ǩ
	 */
	public void insertSpecialkeyWithUserID(@Param("fromUserID") String fromUserID,@Param("specialKey")String specialKey,@Param("keyType")String keyType);
}
