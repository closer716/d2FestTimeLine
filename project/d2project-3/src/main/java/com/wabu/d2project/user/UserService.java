package com.wabu.d2project.user;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	public List<User> getAll() throws Exception{
        return userMapper.getAll();
    }
	public void register(String user_id, String user_name, String user_password, String birthday) {
		userMapper.register(user_id, user_name, user_password, birthday);
	}
}
