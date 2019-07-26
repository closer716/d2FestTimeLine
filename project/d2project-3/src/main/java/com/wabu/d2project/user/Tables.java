package com.wabu.d2project.user;

import com.wabu.d2project.Util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
public class Tables {
	private String tableName;
	
    public String toColumns() {
    	return "tableName";
    }
    public String toValues() {
    	String[] str = {tableName};
    	return Util.makeValues(str);
    }
}