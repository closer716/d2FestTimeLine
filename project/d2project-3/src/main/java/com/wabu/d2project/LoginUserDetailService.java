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
		// �����ؽÿ� �ű��ؽð� �ٸ���� �̷������� �޾� ó���� �� ����.
		// �� @Autowired HttpServletRequest request;
		// request ó��
		System.out.println("LoginUserDetailService ���� "+id);
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
