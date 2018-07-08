package com.BookSystem.MybatisMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.BookSystem.javaBean.*;

public interface MybatisUserMapper {
	/**
	 * �����û��������û���Ϣ
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public User selectUserByUserID(String userName) throws Exception;

	/**
	 * ����email�����û���Ϣ
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public User selectUserByUserEmail(String email) throws Exception;

	/**
	 * �������п����Զ�������û���Ϣ
	 * @param enableAutoBorrowCode
	 * @return
	 * @throws Exception
	 */
	public List<User> findUsersByEnableAutoBorrow(int enableAutoBorrowCode) throws Exception;

	/**
	 * �������п��������ٵݵ��û�����Ϣ
	 * @param eenableAutoNewBookCode
	 * @return
	 */
	public List<User> findUsersByEnableAutoNewBook(int eenableAutoNewBookCode);

	/**
	 * ���ڸ����û�������������û�����
	 * @param userName
	 * @param password
	 */
	public void insertUsersByIDAndPassword(@Param("userName") String userName,@Param("password") String password);
	
	/**
	 * �����û���Email���ǳƸ����û�������
	 * @param email
	 * @param nickName
	 * @param userName
	 * @return
	 */
	public int updateUsersByEmailAndNickName(@Param("email") String email,@Param("nickName") String nickName,@Param("userName") String userName) throws Exception;

	public void updateUserEmail(@Param("email") String email,@Param("userName") String userName) throws Exception;
}
