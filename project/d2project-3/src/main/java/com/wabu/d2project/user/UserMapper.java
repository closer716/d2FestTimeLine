package com.wabu.d2project.user;

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
	public void profileRegister(@Param("id") String id, @Param("name")String name, @Param("birthday") String birthday, @Param("country") String country, 
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
	 * Delete all read notifications
	 */
	public void deleteNotification(@Param("id") String id) throws Exception;
	
	/**
	 * Insert friend into friends table of 'id' and 'friendId'
	 */
	public void beFriend(@Param("id") String id, @Param("friendId") String friendId) throws Exception;
	
	/**
	 * Delete friend from friends table of 'id' and 'friendId'
	 */
	public void deleteFriend(@Param("id") String id, @Param("friendId") String friendId) throws Exception;
	
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
}