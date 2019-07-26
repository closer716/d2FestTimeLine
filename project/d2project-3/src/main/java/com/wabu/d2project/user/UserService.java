package com.wabu.d2project.user;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wabu.d2project.Util;
import com.wabu.d2project.post.PostDto;

@Service
public class UserService {
		
	@Autowired
	private UserMapper userMapper;
	public static final int frontIdIndex = 1;
	Calendar cal = Calendar.getInstance();

	public void createTable() throws Exception{
		
		userMapper.createTable("tables",
				"tableName VARCHAR(20) PRIMARY KEY");
		createBaseTable();
		String yearMonth=Util.makeSuffix(new Date());
		String suffix="";
		for(int i=97; i<123 ; i++){
			suffix= yearMonth+Character.toString((char)i);

			createPostWithSuffix(suffix);
		}
		for(int i=48; i<58; i++){
			suffix=yearMonth+Character.toString((char)i);
			createPostWithSuffix(suffix);
		}
	}
	public void createBaseTable() throws Exception{
		userMapper.createTable("user", 
				"id VARCHAR(20) PRIMARY KEY," +
				"password VARCHAR(16)," +
				"name VARCHAR(20)," +
				"sex TINYINT(1)," +
				"birthday DATE," +
				"city INT," +
				"school INT," +
				"office INT," +
				"registrationDate DATE");
		userMapper.insertIntoTable("tables", "tableName", "\"user\"");
		userMapper.createTable("friend",
				"id VARCHAR(20)," +
				"friendId VARCHAR(20)," +
				"foreign key(id)\r\n"+ 
				"references user(id) on update cascade on delete cascade");	
		userMapper.insertIntoTable("tables", "tableName", "\"friend\"");
		userMapper.createTable("notification",
				"id VARCHAR(20) NOT NULL," +
				"notificationId INT AUTO_INCREMENT NOT NULL key," +
				"friendId VARCHAR(20)," + 
				"content TINYINT(1)," + 
				"date DATETIME," +
				"foreign key(id)\r\n" + 
				"references user(id) on update cascade on delete cascade");
		userMapper.insertIntoTable("tables", "tableName", "\"notification\"");
	}
	
	public void createPostWithSuffix(String suffix) throws Exception{
		userMapper.createTable("post_"+suffix,
				"id VARCHAR(20)," +
				"postId VARCHAR(25)");
		userMapper.insertIntoTable("tables", "tableName", "\""+"post_"+suffix+"\"");
	}

	public User[] getUserTable(String columns, String suffix) throws Exception{
		return userMapper.getUserTable(columns, suffix);
	}
	
	public Notification[] getNotificationTable(String columns, String suffix) throws Exception{
		return userMapper.getNotificationTable(columns,suffix);
	}
	
	public Friend[] getFriendTable(String columns, String suffix) throws Exception{
		return userMapper.getFriendTable(columns,suffix);
	}
	
	public void notificationRegister(Notification notification)throws Exception{
		userMapper.insertIntoTable("notification", 
				notification.toColumns(), notification.toValues());
	}
	
	public void addPost(PostDto mongoPost) throws Exception{
		String suffix=Util.makeSuffix(mongoPost.getDate());
		UserPost post = new UserPost(mongoPost.getUserId(), Util.objectIdtoString(mongoPost.getId()));
		Friend[] friend = getFriendTable("*", "friend WHERE id="+"\""+post.getId()+"\"");
		for(int i=0 ; i<friend.length ; i++) {
			String[] str={friend[i].getFriendId(), post.getPostId()};
			userMapper.insertIntoTable("post_"+suffix+friend[i].getFriendId().substring(0,frontIdIndex), post.toColumns(), Util.makeValues(str));
		}
	}
	
	public void deleteNotification(String id, String notificationId) throws Exception{
		userMapper.deleteRecord("notification", "notificationId= \""+notificationId+"\"");
	}
	
	public void addFriend(Friend friendA, Friend friendB) throws Exception{
		if(!isFriend(friendA.getId(), friendA.getFriendId())) {
			userMapper.insertIntoTable("friend", friendA.toColumns(), friendA.toValues());
			userMapper.insertIntoTable("friend",  friendB.toColumns(), friendB.toValues());
		}
	}
	public boolean isFriend(String idA, String idB) throws Exception{
		Friend[] tmp = getFriendTable("id, friendId", "friend WHERE id=\""+idA+"\""+" AND id=\""+idB+"\"");
		if(tmp.length!=0)
			return true;
		return false;
	}
	
	public void deleteFriend(String id, String friendId) throws Exception{
		userMapper.deleteRecord("friend", "id="+"\""+id+"\""+"AND id="+"\""+friendId+"\"");
		userMapper.deleteRecord("friend", "id="+"\""+friendId+"\""+"AND id="+"\""+id+"\"");
	}

	public void dropAllTable() throws Exception{
		if(!isTableExist("d2", "tables"))
			return;
		Tables[] tables = userMapper.getTableTable("tableName", 
				"tables WHERE tableName like \"post%\" OR tableName = \"notification\" OR tableName = \"friend\"");
		for(int i=0 ; i<tables.length ; i++){
			userMapper.dropTable(tables[i].getTableName());
		}
		tables = userMapper.getTableTable("tableName", "tables");
		for(int i=0 ; i<tables.length ; i++){
			userMapper.dropTable(tables[i].getTableName());
		}
		userMapper.dropTable("tables");
	}
	
	public void deleteUser(User user) throws Exception{
		String suffix;
		cal.setTime(new Date());
		int presentYear = cal.get(Calendar.YEAR);
		int presentMonth = cal.get(Calendar.MONTH);
		cal.setTime(user.getRegistrationDate());
		int year=cal.get(Calendar.YEAR), month=cal.get(Calendar.MONTH);	
		userMapper.deleteRecord("notification", "id="+"\""+user.getId()+"\"");
		while(true){
			suffix=Integer.toString(100*(year-2000)+month)+user.getId().substring(0,frontIdIndex);
			userMapper.deleteRecord("post_"+suffix, "id="+"\""+user.getId()+"\"");
			if(year==presentYear && month==presentMonth)
				break;
			cal.add(Calendar.MONTH, 1);
		}
	}
	public boolean isTableExist(String dbName, String tableName) throws Exception{
		if(userMapper.isExist(dbName,tableName)== null) {
			return false;
		}
		return true;
	}
	
	public void userRegister(User user) throws Exception{
		User[] tmp = getUserTable("id", "user WHERE id=\""+user.getId()+"\"");
		if(tmp.length==0)
			userMapper.insertIntoTable("user", user.toColumns(), user.toValues());
	}
}
