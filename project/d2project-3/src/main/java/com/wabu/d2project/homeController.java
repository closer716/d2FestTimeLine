package com.wabu.d2project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wabu.d2project.user.UserService;

@Controller
@RequestMapping(value="/")
public class homeController {
	@Autowired
	private UserService dao;
	
	@RequestMapping(value="/home")
	protected String home() {
		return "contents/home";
	}
	
	@RequestMapping(value="/login")
	protected String login() {
		return "contents/login";
	}
	
	@RequestMapping(value="/friendSearch")
	protected String friendSearch() {
		return "contents/friendSearch";
	}
	
	@RequestMapping(value="/profile")
	protected String profile() {
		return "contents/profile";
	}
	
	@RequestMapping(value="/register")
	protected String register() { 
		return "contents/register";
	}
}