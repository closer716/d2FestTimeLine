package com.wabu.d2project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wabu.d2project.user.User;
import com.wabu.d2project.user.UserService;

@Service
public class LoginUserDetailService implements UserDetailsService{
	@Autowired
	UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException
	{
		User user=null;
		try {
			user = userService.getUserById(id);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return user;

	}
	public User save(User user) throws Exception{
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userService.userRegister(user);
	}
	
}
