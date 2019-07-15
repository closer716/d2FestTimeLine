package com.wabu.d2project.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserMapper{
	public List<User> getAll() throws Exception;
	public void register(@Param("user_id") String user_id, @Param("user_name")String user_name, @Param("user_password")String user_password, @Param("birthday")String birthday);
}