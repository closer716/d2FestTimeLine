package com.wabu.d2project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	public List<User> getAll() throws Exception{
        return userMapper.getAll();
    }
}
