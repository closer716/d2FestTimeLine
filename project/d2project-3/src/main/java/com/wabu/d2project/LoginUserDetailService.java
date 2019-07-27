package com.wabu.d2project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wabu.d2project.user.User;
import com.wabu.d2project.user.UserService;

@Service
public class LoginUserDetailService implements UserDetailsService{
	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException
	{
		// 기존해시와 신규해시가 다를경우 이런식으로 받아 처리할 수 있음.
		// 위 @Autowired HttpServletRequest request;
		// request 처리
		System.out.println("LoginUserDetailService 들어옴 "+id);
		User user=null;
		try {
			user = userService.getUserById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user.toString());
		
		return new LoginDetails(user);
	}
}
