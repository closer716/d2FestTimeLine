package com.wabu.d2project.user;

import java.text.SimpleDateFormat;
import java.util.Date;

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
public class User {
    @Id
    private String id;
    
    private String password;
    
    private String name;
    
    private boolean sex;
    
    private String birthday;
    
    private int city;
    
    private int school;
    
    private int office;
    
    private Date registrationDate;
    
    @Override
    public String toString() {
        return String.format(
                "profile[id=%s, birthday='%s']",
                id, birthday.toString());
    }
    
    public String toValues() {
    	String strSex;
    	SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd");
    	if(sex=true)
    		strSex="1";
    	else
    		strSex="0";
    	String[] str = {id, password, name, strSex, birthday.toString(), Integer.toString(city), Integer.toString(school), Integer.toString(office), formattedDate.format(registrationDate)};
    	return Util.makeValues(str);
    }
    
    public String toColumns() {
    	return "id, password, name, sex, birthday, city, school, office, registrationDate";
    }
}
