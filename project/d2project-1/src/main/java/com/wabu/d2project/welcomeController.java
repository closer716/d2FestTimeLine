package com.wabu.d2project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value="/")
public class welcomeController {
	@RequestMapping(value="/")
	public String home() {
		return "home";
	}
	@RequestMapping(value="/login")
	public String login() {
		return "login";
	}
	@GetMapping("/loginControll")
	public String test(@RequestParam("id") String id,@RequestParam("password") String password){
		return id+password;
	}
	@RequestMapping(value="/mynews")
	public String news() {
		return "myNews";
	}
	@RequestMapping(value="/profile")
	public String profile() {
		return "profile";
	}
	@RequestMapping(value="/recommend")
	public String recommend() {
		return "recommendFriends";
	}
}
