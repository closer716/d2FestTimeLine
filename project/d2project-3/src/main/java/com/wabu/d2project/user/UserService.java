package com.wabu.d2project.user;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
		
	@Autowired
	private UserMapper userMapper;
	
	public void createUserTable() throws Exception{
		userMapper.createTable("user", 
				"id VARCHAR(20) PRIMARY KEY," +
				"password VARCHAR(16)");
	}
	
	public void createProfileTable() throws Exception{
		userMapper.createTable("profile", 
				"id VARCHAR(20)," +
				"name VARCHAR(20)," +
				"birthday DATE," +
				"country VARCHAR(50)," +
				"elmSchool VARCHAR(20)," +
				"midSchool VARCHAR(20)," +
				"highSchool VARCHAR(20)," +
				"univSchool VARCHAR(20)," +
				"office VARCHAR(20)," +
				"foreign key(id)\r\n" + 
				"references user(id) on update cascade");
	}
	
	public void register(String id, String name, String password, String birthday, String country) throws Exception {
		userRegister(id, password);
		profileRegister(id, name, birthday, country);
		userMapper.createTable("notification_"+id,
				"notificationId INT NOT NULL AUTO_INCREMENT PRIMARY KEY," + 
				"friendId VARCHAR(20)," + 
				"notificationContent VARCHAR(20)," + 
				"isRead TINYINT(1) DEFAULT false");
		userMapper.createTable("friends_"+id,
				"friendId VARCHAR(20) PRIMARY KEY");
		userMapper.createTable("myPosts_"+id,
				"postId VARCHAR(24) PRIMARY KEY");
		userMapper.createTable("posts_"+id,
				"postId VARCHAR(24) PRIMARY KEY");
	}
	
	public List<User> getUserTable(String columns) throws Exception{
		return userMapper.getUserTable(columns);
	}
	
	public List<Profile> getProfleTable(String columns) throws Exception{
		return userMapper.getProfileTable(columns);
	}
	
	public List<Notification> getNotificationTable(String id) throws Exception{
		return userMapper.getNotificationTable("notificationId, friendId, notificationContent, isRead",id);
	}
	
	public List<Friend> getFriendTable(String id) throws Exception{
		return userMapper.getFriendTable("friendId", id);
	}
	
	public String[] getFriendsId(String id) throws Exception{
		return userMapper.selectFromTable("friendId", "friends_"+id);
	}
	
	public String[] getUserId()throws Exception{
		return userMapper.selectFromTable("id", "user");
	}
	
	public String[] selectFromTableWhere(String id, String friendId) throws Exception{
		return userMapper.selectFromTableWhere("friendId", "friends_"+id, friendId);
	}
	
	public void notificationRegister(String id, String friendId, int notificationContent)throws Exception{
		String content;
		if(notificationContent==0)
			content= "님이 친구 요청을 하였습니다.";
		else
			content= "님이 친구 요청을 수락하였습니다.";
		List<String> str = Arrays.asList(friendId, content);
		userMapper.insertIntoTable("notification_"+id, "friendId, notificationContent", makeValues(str));
	}
	
	public void postRegister(String tableName, String postId) throws Exception{
		userMapper.insertIntoTable(tableName, "postId", "\""+postId+"\"" );
	}
	
	public void readNotification(String id, String notificationId) throws Exception{
		userMapper.readNotification(id, notificationId);
	}
	
	public void deleteNotification(String id) throws Exception{
		userMapper.deleteRecord("notification_"+id, "isRead", "1");
	}
	
	public void addFriend(String id, String friendId) throws Exception{
		List<String> str = Arrays.asList(friendId);
		userMapper.insertIntoTable("friends_"+id, "friendId", makeValues(str));
		str = Arrays.asList(id);
		userMapper.insertIntoTable("friends_"+friendId, "friendId", makeValues(str));
	}
	
	public void deleteFriend(String id, String friendId) throws Exception{
		userMapper.deleteRecord("friends_"+id, "friendId", friendId);
		userMapper.deleteRecord("friends_"+friendId, "friendId", id);
	}
	
	private void dropUserTables(String id) throws Exception{
		userMapper.dropTable("friends_"+id);
		userMapper.dropTable("notification_"+id);
		userMapper.dropTable("posts_"+id);
		userMapper.dropTable("myPosts_"+id);
	}
	
	public void deleteUser(String id) throws Exception{
		dropUserTables(id);
		userMapper.deleteRecord("profile", "id", id);
		userMapper.deleteRecord("user", "id", id);
	}
	
	private void userRegister(String id, String password) throws Exception{
		List<String> str = Arrays.asList(id,password);
		userMapper.insertIntoTable("user", "id,password", makeValues(str));
	}
	
	private void profileRegister(String id, String name, String birthday, String country) throws Exception{
		List<String> str = Arrays.asList(id,name,birthday.toString(),country);
		userMapper.insertIntoTable("profile", "id, name, birthday, country", makeValues(str));
	}
	
	private String makeValues(List<String> str) {
		String result = new String();
		int i = 0;
		while(true) {
			result+="\""+str.get(i++)+"\"";
			if(i==str.size())
				break;
			result+=", ";
		}
		return result;
	}
}
