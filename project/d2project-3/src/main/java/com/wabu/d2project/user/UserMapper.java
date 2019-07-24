package com.wabu.d2project.user;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserMapper{

	public void readNotification(@Param("id") String id, @Param("notificationId") String notificationId) throws Exception;
	
	public User[] getUserTable(@Param("columns") String columns);
	
	public Profile[] getProfileTable(@Param("columns") String columns);

	public Notification[] getNotificationTable(@Param("columns") String columns, @Param("id") String id) throws Exception;

	public Friend[] getFriendTable(@Param("columns") String columns, @Param("id") String id) throws Exception;
	
	public void dropTable(@Param("tableName") String tableName) throws Exception;
	
	public void createTable(@Param("tableName") String tableName, @Param("columns") String columns) throws Exception;
	
	public void insertIntoTable(@Param("tableName") String tableName, @Param("columns") String columns, @Param("values") String values) throws Exception;
	
	public void deleteRecord(@Param("tableName") String tableName, @Param("keyColumn") String keyColumn, @Param("value") String value)throws Exception;
	
	public DataContainer[] selectFromTable(@Param("columnName") String columnName, @Param("tableName") String tableName) throws Exception;
	
	public DataContainer[] selectFromTableWhere(@Param("columnName") String columnName, @Param("tableName") String tableName, @Param("value") String value) throws Exception;
}