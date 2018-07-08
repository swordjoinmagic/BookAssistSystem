package com.BookSystem.MybatisMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.BookSystem.javaBean.*;

public interface MybatisUserMapper {
	/**
	 * 根据用户名查找用户信息
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public User selectUserByUserID(String userName) throws Exception;

	/**
	 * 根据email查找用户信息
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public User selectUserByUserEmail(String email) throws Exception;

	/**
	 * 查找所有开启自动续借的用户信息
	 * @param enableAutoBorrowCode
	 * @return
	 * @throws Exception
	 */
	public List<User> findUsersByEnableAutoBorrow(int enableAutoBorrowCode) throws Exception;

	/**
	 * 查找所有开启新书速递的用户的信息
	 * @param eenableAutoNewBookCode
	 * @return
	 */
	public List<User> findUsersByEnableAutoNewBook(int eenableAutoNewBookCode);

	/**
	 * 用于根据用户名和密码插入用户数据
	 * @param userName
	 * @param password
	 */
	public void insertUsersByIDAndPassword(@Param("userName") String userName,@Param("password") String password);
	
	/**
	 * 根据用户的Email和昵称更新用户的数据
	 * @param email
	 * @param nickName
	 * @param userName
	 * @return
	 */
	public int updateUsersByEmailAndNickName(@Param("email") String email,@Param("nickName") String nickName,@Param("userName") String userName) throws Exception;

	public void updateUserEmail(@Param("email") String email,@Param("userName") String userName) throws Exception;
}
