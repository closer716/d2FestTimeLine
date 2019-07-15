package com.wabu.d2project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wabu.d2project.user.User;
import com.wabu.d2project.user.UserService;

@Controller
@RequestMapping(value="/")
public class homeController{
	@Autowired
	private UserService userService;
	@Autowired
    UserService testService;
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
	
	@PostMapping("register/confirm")
    public String query(@RequestParam("user_id") String user_id, @RequestParam("user_password") String user_password, @RequestParam("user_name") String user_name,
    		@RequestParam("birthday") String birthday) throws Exception{
        testService.register(user_id, user_password, user_name, birthday);
        return "contents/test";
    }
	
	
}
