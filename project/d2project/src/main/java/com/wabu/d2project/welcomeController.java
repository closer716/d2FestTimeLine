package com.wabu.d2project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class welcomeController{
	
	
	@RequestMapping(value="/")
	public String meetingListView() {
		return "welcome";
	}
	@RequestMapping(value="/great")
	public String abc() {
		
		return "ttt/index";
	}
}
