package com.wabu.d2project.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.wabu.d2project.Util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
public class User implements UserDetails{
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
                "user[id=%s, password='%s']",
                id, password);
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

    @Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
