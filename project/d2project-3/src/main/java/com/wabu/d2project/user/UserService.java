package com.wabu.d2project.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
		
	@Autowired
	private UserMapper userMapper;
	
	public void register(String id, String name, String password, String birthday, String country) throws Exception {
		String query="CREATE TABLE friend_"+id;
		query = query+"(boardId number primary key))";
		userMapper.createNotificationTable(id);
		userMapper.createFriendsTable(id);
		userMapper.userRegister(id, password);
		userMapper.profileRegister(id, name, birthday, country, "profile");
	}
	
	public void notify(String id, String friendId, String notificationContent)throws Exception{
		userMapper.notify(id, friendId, notificationContent);
	}
	
	public void readNotification(String id, String notificationId) throws Exception{
		userMapper.readNotification(id, notificationId);
	}
	
	public void deleteNotification(String id) throws Exception{
		deleteNotification(id);
	}
	
	public void beFriend(String id, String friendId) throws Exception{
		beFriend(id,friendId);
	}
	
	public void deleteFriend(String id, String friendId) throws Exception{
		deleteFriend(id,friendId);
	}
	
	public List<Notification> getNotificationTable(String id) throws Exception{
		return getNotificationTable(id);
	}
	
	public List<String> getFriendTable(String id) throws Exception{
		return getFriendTable(id);
	}
}
