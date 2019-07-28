package com.wabu.d2project.user.dataContainer;

import org.springframework.data.annotation.Id;

import com.wabu.d2project.Util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
public class Friend {
	@Id
	private String id;
    private String friendId;
    
    public String toColumns() {
    	return "id, friendId";
    }
    public String toValues() {
    	String[] str = {id, friendId};
    	return Util.makeValues(str);
    }
}
