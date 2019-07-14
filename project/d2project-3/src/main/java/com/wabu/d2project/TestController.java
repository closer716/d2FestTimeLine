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
	
	@GetMapping("/register")
	protected String register(@RequestParam("name") String name, @RequestParam("address") String address){
		System.out.println(userService.addUser(new UserDto(name, address)));
		return "/contents/home";
	}
}
