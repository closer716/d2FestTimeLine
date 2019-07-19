package com.wabu.d2project.user;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserMapper{
	/**
	 * Insert user into user table
	 * @param id
	 * @param password
	 */
	public void userRegister(@Param("id") String id, @Param("password")String password) throws Exception;
	
	/**
	 * Insert user profile into profile table
	 * @param id 
	 * @param name
	 * @param birthday
	 * @param country
	 * @param profile
	 */
	public void profileRegister(@Param("id") String id, @Param("name")String name, @Param("birthday") Date birthday, @Param("country") String country, 
			@Param("profile") String profile) throws Exception;
	
	/**
	 * Create friends table for 'id'
	 */
	public void createFriendsTable(@Param("id") String id) throws Exception;
	
	/**
	 * Create notification table for 'id'
	 */
	public void createNotificationTable(@Param("id") String id) throws Exception;
	
	/**
	 * Insert 'notificationContent' from 'friendId' into 'id's notification table
	 */
	public void notify(@Param("id") String id,@Param("friendId") String friendId,@Param("notificationContent") String notificationContent) throws Exception;
	
	/**
	 * Check for read notification
	 */
	public void readNotification(@Param("id") String id, @Param("notificationId") String notificationId) throws Exception;

	
	/**
	 * Insert friend into friends table of 'id' and 'friendId'
	 */
	public void addFriend(@Param("id") String id, @Param("friendId") String friendId) throws Exception;
	
	public void addFriendForTest(@Param("id") String id, @Param("friendId") String friendId) throws Exception;
	

	/**
	 * Select notifications from notification table of 'id'
	 * @return List of notification
	 */
	public List<Notification> getNotificationTable(@Param("id") String id) throws Exception;
	
	/**
	 * Get friendsId from friends table of 'id'
	 * @return friendsId
	 */
	public List<String> getFriendTable(@Param("id") String id) throws Exception;
	
	public void dropTable(@Param("tableName") String tableName) throws Exception;
	
	public void deleteRecord(@Param("tableName") String tableName, @Param("keyColumn") String keyColumn, @Param("value") String value)throws Exception;
	
	public String[] selectFromTable(@Param("columnName") String columnName, @Param("tableName") String tableName) throws Exception;
	public String[] selectFromTableWhere(@Param("columnName") String columnName, @Param("tableName") String tableName, @Param("value") String value) throws Exception;
}