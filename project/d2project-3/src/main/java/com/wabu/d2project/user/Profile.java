package com.wabu.d2project.user;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.wabu.d2project.Functions;

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
    
    private String country;
    
    private String city;
    
    private String elmSchool;
    
    private String midSchool;
    
    private String highSchool;
    
    private String univSchool;
    
    private String office;
    
    @Override
    public String toString() {
        return String.format(
                "profile[id=%s, country='%s', birthday='%s']",
                id, country, birthday.toString());
    }
    
    public String toValues() {
    	String strSex;
    	if(sex=true)
    		strSex="1";
    	else
    		strSex="0";
    	String[] str = {id, name, strSex, birthday.toString(), country, city, elmSchool, midSchool, highSchool, univSchool, office};
    	return Functions.makeValues(str);
    }
    
    public String toColumns() {
    	return "id, name, sex, birthday, country, city, elmSchool, midSchool, highSchool, univSchool, office";
    }
}
