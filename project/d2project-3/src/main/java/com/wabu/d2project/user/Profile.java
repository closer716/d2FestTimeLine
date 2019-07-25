package com.wabu.d2project.user;

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
public class Profile {
    @Id
    private String id;
    
    private String name;
    
    private boolean sex;
    
    private String birthday;
    
    private int city;
    
    private int school;
    
    private int office;
    
    @Override
    public String toString() {
        return String.format(
                "profile[id=%s, birthday='%s']",
                id, birthday.toString());
    }
    
    public String toValues() {
    	String strSex;
    	if(sex=true)
    		strSex="1";
    	else
    		strSex="0";
    	String[] str = {id, name, strSex, birthday.toString(), Integer.toString(city), Integer.toString(school), Integer.toString(office)};
    	return Util.makeValues(str);
    }
    
    public String toColumns() {
    	return "id, name, sex, birthday, city, school, office";
    }
}
