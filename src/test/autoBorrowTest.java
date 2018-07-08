package test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;

import com.BookSystem.DataBaseManagement.MybatisManager;
import com.BookSystem.HttpUtil.HttpClientUtil;
import com.BookSystem.MybatisMapper.MybatisUserMapper;
import com.BookSystem.javaBean.User;

public class autoBorrowTest {
	public static void main(String[] args) {
		// ��һ�����ҵ������Զ�������û�
		SqlSession session = MybatisManager.getSqlsessionfactory().openSession();
		MybatisUserMapper userMapper = session.getMapper(MybatisUserMapper.class);
		
		List<User>users = null;
		try {
			users = userMapper.findUsersByEnableAutoBorrow(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(users);
		
		if(users!=null) {
			for(User user:users) {
				String userEmail = user.getEmail();
				
				// �����Զ�����Ľӿ�
				JSONObject jsonObject = HttpClientUtil.get("http://localhost:8088/interface/autoBorrow?userID="+user.getUserName());
				
				boolean status = jsonObject.getBoolean("status");
				String errorMsg = jsonObject.getString("errorMsg");
				
				
			}
		}
	}
}
