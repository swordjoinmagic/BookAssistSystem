package test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.BookSystem.MybatisMapper.MybatisCommentMapper;
import com.BookSystem.MybatisMapper.MybatisSpecialKeyMapper;
import com.BookSystem.MybatisMapper.MybatisUserMapper;
import com.BookSystem.javaBean.Comment;
import com.BookSystem.javaBean.SpecialKey;
import com.BookSystem.javaBean.User;

public class MybatisTest {
	public static void main(String[] args) throws Exception {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session = sqlSessionFactory.openSession();
//		MybatisUserMapper mapper = session.getMapper(MybatisUserMapper.class);
//		User user = mapper.selectUserByUserID("16240011");
//		System.out.println(user);
//		List<User>list = mapper.findUsersByEnableAutoNewBook(0);
		
//		mapper.insertUsersByIDAndPassword("16240013", "16240013");
//		session.commit();
		
//		mapper.updateUsersByEmailAndNickName("xxx", null, "16240013");
//		session.commit();
		
//		MybatisCommentMapper mapper = session.getMapper(MybatisCommentMapper.class);
////		List<Comment>list = mapper.findComments("9787115385734", new RowBounds(0, 20));
//		List<Comment>list = mapper.findCommentsWithISBNAndUserID("9787115385734", "16240011", new RowBounds(0,20));
//		System.out.println(list);
		
		MybatisSpecialKeyMapper mapper = session.getMapper(MybatisSpecialKeyMapper.class);
		List<SpecialKey>list = mapper.findSpecialKeysWithUserID("16240011");
		
		System.out.println(list);
	}
}
