package com.wabu.d2project.user;

import java.util.Date;

import com.wabu.d2project.Util;

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
	
    public String toColumns() {
    	return "id, postId";
    }
    public String toValues() {
    	String[] str = {id, postId};
    	return Util.makeValues(str);
    }
}
