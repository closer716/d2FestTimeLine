package com.wabu.d2project;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

public class LoginDetails extends User {
	private static final long serialVersionUID = 1L;

	public LoginDetails(com.wabu.d2project.user.User user)
	{
		super
		(
			user.getId(),
			user.getPassword(),
			AuthorityUtils.createAuthorityList()
		);
		System.out.println("LoginDetails µé¾î¿È");
		System.out.println(user.toString());
	}
}
