package com.wabu.d2project.user;

import org.apache.ibatis.annotations.Param;

public interface UserMapper{

	public void updateRecord(@Param("tableName") String tableName, @Param("set") String set, @Param("suffix") String suffix) throws Exception;
	
	public User[] getUserTable(@Param("columns") String columns, @Param("suffix") String suffix);

	public Notification[] getNotificationTable(@Param("columns") String columns, @Param("suffix") String suffix) throws Exception;

	public Friend[] getFriendTable(@Param("columns") String columns, @Param("suffix") String suffix) throws Exception;
	
	public Tables[] getTableTable(@Param("columns") String columns, @Param("suffix") String suffix) throws Exception;
	
	public void dropTable(@Param("tableName") String tableName) throws Exception;
	
	public void createTable(@Param("tableName") String tableName, @Param("columns") String columns) throws Exception;
	
	public void insertIntoTable(@Param("tableName") String tableName, @Param("columns") String columns, @Param("values") String values) throws Exception;
	
	public void deleteRecord(@Param("tableName") String tableName, @Param("suffix") String suffix)throws Exception;
	
	public DataContainer[] selectFromTable(@Param("columnName") String columnName, @Param("suffix") String suffix) throws Exception;
	
	public String isExist(@Param("dbName") String dbName, @Param("tableName") String tableName)throws Exception;
}