package com.wabu.d2project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wabu.d2project.user.UserDto;
import com.wabu.d2project.user.UserService;

@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	private UserService userService; 
	
	@PostMapping("/register")
	protected String register(@RequestParam("user_id") String user_id, @RequestParam("user_password") String user_password,
			@RequestParam("user_name") String user_name, @RequestParam("birthday") String birthday){
		System.out.println(userService.addUser(new UserDto(user_id, user_password, user_name, birthday)));
		userService.printDB();
		return "/contents/home";
	}
}
