package com.wabu.d2project.user;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
		
	@Autowired
	private UserMapper userMapper;
	
	public void register(String id, String name, String password, Date birthday, String country) throws Exception {
		userMapper.userRegister(id, password);
		userMapper.profileRegister(id, name, birthday, country, "profile");
		userMapper.createNotificationTable(id);
		userMapper.createFriendsTable(id);
	}
	
	public void notify(String id, String friendId, String notificationContent)throws Exception{
		userMapper.notify(id, friendId, notificationContent);
	}
	
	public void readNotification(String id, String notificationId) throws Exception{
		userMapper.readNotification(id, notificationId);
	}
	
	public void deleteNotification(String id) throws Exception{
		userMapper.deleteRecord("notification_"+id, "isRead", "1");
	}
	
	public void addFriend(String id, String friendId) throws Exception{
		userMapper.addFriend(id,friendId);
		userMapper.addFriend(friendId,id);
	}
	
	public void addFriendForTest(String id, String friendId) throws Exception{
		userMapper.addFriend(id,friendId);
		userMapper.addFriend(friendId,id);
	}
	
	public void deleteFriend(String id, String friendId) throws Exception{
		userMapper.deleteRecord("friends_"+id, "friendId", friendId);
		userMapper.deleteRecord("friends_"+friendId, "friendId", id);
	}
	
	public List<Notification> getNotificationTable(String id) throws Exception{
		return userMapper.getNotificationTable(id);
	}
	
	public List<String> getFriendTable(String id) throws Exception{
		return userMapper.getFriendTable(id);
	}
	
	public void dropFriendsAndNotificationTable(String id) throws Exception{
		userMapper.dropTable("friends_"+id);
		userMapper.dropTable("notification_"+id);
	}
	
	public void deleteUser(String id) throws Exception{
		dropFriendsAndNotificationTable(id);
		userMapper.deleteRecord("profile", "id", id);
		userMapper.deleteRecord("user", "id", id);
	}
	
	public String[] getUserId()throws Exception{
		return userMapper.selectFromTable("id", "user");
	}
	public String[] selectFromTableWhere(String id, String friendId) throws Exception{
		return userMapper.selectFromTableWhere("friendId", "friends_"+id, friendId);
	}
}
