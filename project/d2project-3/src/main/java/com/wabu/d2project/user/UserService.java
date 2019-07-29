package com.wabu.d2project.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wabu.d2project.post.PostDto;
import com.wabu.d2project.user.dataContainer.Friend;
import com.wabu.d2project.user.dataContainer.Notification;
import com.wabu.d2project.user.dataContainer.Tables;
import com.wabu.d2project.user.dataContainer.UserPost;
import com.wabu.d2project.util.Util;

@Service
public class UserService {
		
	@Autowired
	private UserMapper userMapper;
	public static final int frontIdIndex = 1;
	Calendar cal=Calendar.getInstance();
	/**
	 * Create all tables
	 * for construct database system
	 * @throws Exception
	 */
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
		userMapper.insertIntoTable("tables", "tableName", "\"tables\"");
	}
	/**
	 * Create user, friend, notification tables
	 * @throws Exception
	 */
	public void createBaseTable() throws Exception{
		userMapper.createTable("user", 
				"id VARCHAR(20) PRIMARY KEY," +
				"password VARCHAR(70)," +
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
				"foreign key(id)"+ 
				"references user(id) on update cascade on delete cascade");	
		userMapper.insertIntoTable("tables", "tableName", "\"friend\"");
		userMapper.createTable("notification",
				"id VARCHAR(20) NOT NULL," +
				"notificationId INT AUTO_INCREMENT NOT NULL key," +
				"friendId VARCHAR(20)," + 
				"content TINYINT(1)," + 
				"date DATETIME," +
				"foreign key(id)" + 
				"references user(id) on update cascade on delete cascade");
		userMapper.insertIntoTable("tables", "tableName", "\"notification\"");
	}
	/**
	 * Create Post Table (Post_yymma)
	 * which 'a' is the first word of user id 
	 * @param suffix
	 * @throws Exception
	 */
	public void createPostWithSuffix(String suffix) throws Exception{
		userMapper.createTable("post_"+suffix,
				"id VARCHAR(20)," +
				"postId VARCHAR(25)," +
				"date DATETIME");
		userMapper.insertIntoTable("tables", "tableName", "\""+"post_"+suffix+"\"");
	}
	
	/**
	 * 
	 * @param id
	 * @param registrationDate
	 * @param from
	 * @param num
	 * @return 'num' numbers of postId from 'from'
	 * @throws Exception
	 */
	public ArrayList<String> getPostId(String id, Date registrationDate, int from, int num) throws Exception{
		String suffix;
		cal.setTime(new Date());
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		cal.setTime(registrationDate);
		int registrationYear=cal.get(Calendar.YEAR), registrationMonth=cal.get(Calendar.MONTH);
		ArrayList<String> result=new ArrayList<String>();
		ArrayList<String> tmp;
		
		for(int i=num; i>0;) {
			suffix=Integer.toString(100*(year-2000)+month)+id.substring(0,frontIdIndex);
			tmp = userMapper.getPostId("postId", "post_"+suffix+" WHERE id=\""+id+"\" ORDER BY date desc LIMIT "+from+", "+i);
			result.addAll(tmp);
			i -= tmp.size();
			if(year==registrationYear && month==registrationMonth)
				break;
			cal.add(Calendar.MONTH, -1);
		}
		
		return result;
	}
	/**
	 * For getting data in User type
	 * @param columns
	 * @param suffix
	 * @return ArrayList<User>
	 * @throws Exception
	 */
	public ArrayList<User> getUserTable(String columns, String suffix) throws Exception{
		return userMapper.getUserTable(columns, suffix);
	}
	/**
	 * 
	 * @param columns
	 * @param suffix
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Notification> getNotificationTable(String columns, String suffix) throws Exception{
		return userMapper.getNotificationTable(columns,suffix);
	}
	/**
	 * For getting data in Friend type
	 * @param columns
	 * @param suffix
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Friend> getFriendTable(String columns, String suffix) throws Exception{
		return userMapper.getFriendTable(columns,suffix);
	}
	/**
	 * For getting data in Notification type
	 * @param notification
	 * @throws Exception
	 */
	public void notificationRegister(Notification notification)throws Exception{
		userMapper.insertIntoTable("notification", 
				notification.toColumns(), notification.toValues());
	}
	
	/**
	 * Add post into post table
	 * @param mongoPost
	 * @throws Exception
	 */
	public void addPost(PostDto mongoPost) throws Exception{
		String suffix=Util.makeSuffix(mongoPost.getDate());
		UserPost post = new UserPost(mongoPost.getUserId(), Util.objectIdtoString(mongoPost.getId()), mongoPost.getDate());
		ArrayList<Friend> friend = getFriendTable("*", "friend WHERE id="+"\""+post.getId()+"\"");
		friend.add(new Friend(mongoPost.getUserId(), mongoPost.getUserId()));
		for(int i=0 ; i<friend.size(); i++) {
			String[] str={friend.get(i).getFriendId(), post.getPostId(), post.getDate()};
			String tableName = "post_"+suffix+friend.get(i).getFriendId().substring(0,frontIdIndex);
			if(!isTableExist(tableName)){
				createPostWithSuffix(suffix+friend.get(i).getFriendId().substring(0,frontIdIndex));
			}
			userMapper.insertIntoTable(tableName, post.toColumns(), Util.makeValues(str));
		}
	}
	
	/**
	 * Delete Notification by Id
	 * @param id
	 * @param notificationId
	 * @throws Exception
	 */
	public void deleteNotification(String notificationId) throws Exception{
		userMapper.deleteRecord("notification", "notificationId= \""+notificationId+"\"");
	}
	
	/**
	 * Insert friend relationship into friend table
	 * @param friendA
	 * @param friendB
	 * @throws Exception
	 */
	public void addFriend(Friend friendA, Friend friendB) throws Exception{
		if(!isFriend(friendA.getId(), friendA.getFriendId())) {
			userMapper.insertIntoTable("friend", friendA.toColumns(), friendA.toValues());
			userMapper.insertIntoTable("friend",  friendB.toColumns(), friendB.toValues());
		}
	}
	/**
	 * Check given two id are in friendship
	 * @param idA
	 * @param idB
	 * @return
	 * @throws Exception
	 */
	public boolean isFriend(String idA, String idB) throws Exception{
		ArrayList<Friend> tmp = getFriendTable("id, friendId", "friend WHERE id=\""+idA+"\""+" AND id=\""+idB+"\"");
		if(tmp.size()!=0)
			return true;
		return false;
	}
	
	/**
	 * Sever two id connection
	 * @param id
	 * @param friendId
	 * @throws Exception
	 */
	public void deleteFriend(String id, String friendId) throws Exception{
		userMapper.deleteRecord("friend", "id="+"\""+id+"\""+"AND id="+"\""+friendId+"\"");
		userMapper.deleteRecord("friend", "id="+"\""+friendId+"\""+"AND id="+"\""+id+"\"");
	}
	
	
	/**
	 * Drop all tables
	 * @throws Exception
	 */
	public void dropAllTable() throws Exception{
		if(!isTableExist("tables"))
			return;
		ArrayList<Tables> tables = userMapper.getTableTable("tableName", 
				"tables WHERE tableName like \"post%\" OR tableName = \"notification\" OR tableName = \"friend\"");
		for(int i=0 ; i<tables.size() ; i++){
			userMapper.dropTable(tables.get(i).getTableName());
		}
		tables = userMapper.getTableTable("tableName", "tables");
		for(int i=0 ; i<tables.size() ; i++){
			userMapper.dropTable(tables.get(i).getTableName());
		}
	}
	
	/**
	 * Delete user from databases
	 * @param user
	 * @throws Exception
	 */
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
	/**
	 * Check the table is exist
	 * @param dbName
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public boolean isTableExist(String tableName) throws Exception{
		ArrayList<Tables> tables = userMapper.getTableTable("tableName", 
				"tables WHERE tableName = \""+tableName+"\"");
		
		if(tables.size() == 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * insert user into user table
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public User userRegister(User user) throws Exception{
		ArrayList<User> tmp = getUserTable("id", "user WHERE id=\""+user.getId()+"\"");
		if(tmp.size()==0)
			userMapper.insertIntoTable("user", user.toColumns(), user.toValues());
		return user;
	}
	
	/**
	 * Get Friend's friends for recommend 
	 * @param userId
	 * @param from
	 * @param num
	 * @return
	 * @throws Exception
	 */
	public ArrayList<User> getFriendsFriend (String userId, int from, int num) throws Exception{
		String str="(" + 
				"	SELECT *" + 
				"	FROM(" + 
				"		SELECT fr.friendId ,COUNT(fr.friendId) AS cnt" + 
				"		FROM (" + 
				"			SELECT *  FROM friend" + 
				"			WHERE friend.id=\""+userId+"\"" + 
				"		)us" + 
				"		JOIN friend AS fr" + 
				"		ON  us.friendId=fr.id AND fr.friendId <> us.id" + 
				"		GROUP BY fr.friendId" + 
				"	)result " + 
				"	WHERE result.friendId not IN (SELECT id FROM notification WHERE friendId=\""+userId+"\" AND content=\"0\") " + 
				" )result2 " + 
				" JOIN user " + 
				" ON user.id=result2.friendId " + 
				" ORDER BY result2.cnt DESC " + 
				" LIMIT "+from+", "+num;
		ArrayList<User> user = userMapper.getUserTable("user.id, result2.cnt as password, user.name, user.birthday, user.city, user.school, user.office", str);
		return user;
	}
	
	/**
	 * Get people who have common things with user
	 * @param user
	 * @param from
	 * @param num
	 * @return
	 * @throws Exception
	 */
	public ArrayList<User> getMayFriend(User user, int from, int num)throws Exception{
		String birthyear = user.getBirthday().substring(0,4);
		String last = Integer.toString(Integer.parseInt(birthyear)-1);
		String next = Integer.toString(Integer.parseInt(birthyear)+1);
		String str="(SELECT id, NAME, birthday, city, school, office, " + 
				" case when office=\""+user.getOffice()+"\" then \"4\"" + 
				" 	when birthday BETWEEN \""+birthyear+"-01-01\" AND \""+birthyear+"-12-31\" and school=\""+user.getSchool()+"\" then \"4\"" + 
				" 	when birthday BETWEEN \""+last+"-01-01\" AND \""+next+"-12-31\" and school=\""+user.getSchool()+"\" then \"3\"" + 
				" 	when birthday BETWEEN \""+last+"-01-01\" AND \""+next+"-12-31\" and city=\""+user.getCity()+"\" then \"2\"" + 
				" 	when city = \""+user.getCity()+"\" then \"1\"" + 
				" ELSE \"0\" END AS cnt" + 
				" FROM user" + 
				" )result" + 
				" WHERE result.cnt<>\"0\" AND result.id not IN (SELECT id FROM notification WHERE friendId=\""+user.getId()+"\" AND content=\"0\") " + 
				" AND id <> \""+user.getId()+"\""+
				" LIMIT "+from+", "+num;
		ArrayList<User> result = userMapper.getUserTable("id, name, birthday, city, school, office", str);
		return result;
	}
	
	/**
	 * Get a user information by Id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public User getUserById(String id) throws Exception{
		ArrayList<User> user = userMapper.getUserTable("*", "user where id="+"\""+id+"\"");
		if(user.size()==0)
			return null;
		else
			return user.get(0);
	}
}
