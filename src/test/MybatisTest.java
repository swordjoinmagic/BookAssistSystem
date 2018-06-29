package test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.BookSystem.MybatisMapper.MybatisUserMapper;
import com.BookSystem.javaBean.User;

public class MybatisTest {
	public static void main(String[] args) throws Exception {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session = sqlSessionFactory.openSession();
		MybatisUserMapper mapper = session.getMapper(MybatisUserMapper.class);
//		User user = mapper.selectUserByUserID("16240011");
//		System.out.println(user);
//		List<User>list = mapper.findUsersByEnableAutoNewBook(0);
		
//		mapper.insertUsersByIDAndPassword("16240013", "16240013");
//		session.commit();
		
		mapper.updateUsersByEmailAndNickName("xxx", "sjm", "16240013");
		session.commit();
		
	}
}
