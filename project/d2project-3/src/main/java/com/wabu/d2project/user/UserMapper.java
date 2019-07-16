package com.wabu.d2project.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserMapper{
	public List<User> getAll() throws Exception;
	public void register(@Param("id") String id, @Param("name")String name, @Param("password")String password, @Param("birthday")String birthday);
}