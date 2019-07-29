package com.wabu.d2project.user.dataContainer;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.wabu.d2project.util.Util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
public class UserPost {
	private String id;
	private String postId;
	private Date date;
	
    public String toColumns() {
    	return "id, postId, date";
    }
    
    public String getDate() {
    	SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = formattedDate.format(date);
    	return strDate;
    }
    
    public String toValues() {
    	String[] str = {id, postId, getDate()};
    	return Util.makeValues(str);
    }
}
