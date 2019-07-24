package com.wabu.d2project.user;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wabu.d2project.Functions;

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
				"sex TINYINT(1)," +
				"birthday DATE," +
				"country VARCHAR(50)," +
				"city VARCHAR(20)," +
				"elmSchool VARCHAR(20)," +
				"midSchool VARCHAR(20)," +
				"highSchool VARCHAR(20)," +
				"univSchool VARCHAR(20)," +
				"office VARCHAR(20)," +
				"foreign key(id)\r\n" + 
				"references user(id) on update cascade");
	}
	
	public void register(User user, Profile profile) throws Exception {
		String id = user.getId();
		userRegister(user);
		profileRegister(profile);
		userMapper.createTable("notification_"+id,
				"notificationId INT NOT NULL AUTO_INCREMENT PRIMARY KEY," + 
				"friendId VARCHAR(20)," + 
				"notificationContent VARCHAR(20)," + 
				"isRead TINYINT(1) DEFAULT false," +
				"foreign key(id)");
		userMapper.createTable("friends_"+id,
				"friendId VARCHAR(20) PRIMARY KEY," +
				"foreign key(id)\r\n");
		userMapper.createTable("posts_"+id,
				"postId VARCHAR(24) PRIMARY KEY," +
				"foreign key(id)\r\n");
	}
	
	public User[] getUserTable(String columns) throws Exception{
		return userMapper.getUserTable(columns);
	}
	
	public Profile[] getProfleTable(String columns) throws Exception{
		return userMapper.getProfileTable(columns);
	}
	
	public Notification[] getNotificationTable(String id) throws Exception{
		return userMapper.getNotificationTable("notificationId, friendId, notificationContent, isRead",id);
	}
	
	public Friend[] getFriendTable(String id) throws Exception{
		return userMapper.getFriendTable("friendId", id);
	}
	
	public DataContainer[] getFriendsId(String id) throws Exception{
		return userMapper.selectFromTable("friendId AS column1", "friends_"+id);
	}
	
	public DataContainer[] getUserId()throws Exception{
		return userMapper.selectFromTable("id AS column1", "user");
	}
	
	public DataContainer[] selectFromTableWhere(String id, String friendId) throws Exception{
		return userMapper.selectFromTable("friendId AS column1", "friends_"+id+" WHERE friendId =\""+friendId+"\"");
	}
	
	public DataContainer[] getFriendsFriend(String userId) throws Exception{
		Friend[] friends = getFriendTable(userId);
		if(friends.length==0)
			return null;
		String str ="(SELECT friendId FROM friends_"+friends[0].getFriendId();
		for(int i=1 ; i<friends.length; i++) {
			str+=" UNION ALL SELECT friendId FROM friends_"+friends[i].getFriendId()+" where friendId <> \""+userId+"\"";
		}
		str+=")p GROUP BY(friendId) ORDER BY COUNT(friendId) desc";
		
		return userMapper.selectFromTable("friendId as column1, count(friendId) as column2",str);
	}
	
	public void notificationRegister(String id, String friendId, int notificationContent)throws Exception{
		String content;
		if(notificationContent==0)
			content= "님이 친구 요청을 하였습니다.";
		else
			content= "님이 친구 요청을 수락하였습니다.";
		String[] str = {friendId, content};
		userMapper.insertIntoTable("notification_"+id, "friendId, notificationContent", Functions.makeValues(str));
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
		userMapper.insertIntoTable("friends_"+id, "friendId", "\""+friendId+"\"");
		userMapper.insertIntoTable("friends_"+friendId, "friendId", "\""+id+"\"");
	}
	
	public void deleteFriend(String id, String friendId) throws Exception{
		userMapper.deleteRecord("friends_"+id, "friendId", friendId);
		userMapper.deleteRecord("friends_"+friendId, "friendId", id);
	}
	
	private void dropUserTables(String id) throws Exception{
		userMapper.dropTable("friends_"+id);
		userMapper.dropTable("notification_"+id);
		userMapper.dropTable("posts_"+id);
	}
	
	public void dropUsreAndProfileTable() throws Exception{
		userMapper.dropTable("profile");
		userMapper.dropTable("user");
	}
	
	public void deleteUser(String id) throws Exception{
		dropUserTables(id);
		userMapper.deleteRecord("profile", "id", id);
		userMapper.deleteRecord("user", "id", id);
	}
	
	private void userRegister(User user) throws Exception{
		userMapper.insertIntoTable("user", user.toColumns(), user.toValues());
	}
	
	private void profileRegister(Profile profile) throws Exception{
		userMapper.insertIntoTable("profile", profile.toColumns(), profile.toValues());
	}
	

}
