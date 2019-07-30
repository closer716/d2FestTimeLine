package com.wabu.d2project.user.dataContainer;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Id;

import com.wabu.d2project.util.Util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
public class Notification {
	@Id
    private String id;

    private String friendId;
    
    private int content;
    
    private Date date;
    
    private String notificationId;
    
    public Notification(String id, String friendId, int content, Date date){
    	this.id = id;
    	this.friendId = friendId;
    	this.content = content;
    	this.date = date;
    }
    
    public String toColumns() {
    	return "id, friendId, content, date";
    }
    
    
    public String toValues() {
		SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = formattedDate.format(date);
		
    	String[] str = {id, friendId, Integer.toString(content),strDate};
    	return Util.makeValues(str);
    }
}
