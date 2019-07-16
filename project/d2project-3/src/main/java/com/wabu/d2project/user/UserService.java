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
	public void register(String id, String name, String password, String birthday) {
		userMapper.register(id, name, password, birthday);
	}
}
