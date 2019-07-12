package com.wabu.d2project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/")
public class homeController {
	
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
	
	@RequestMapping(value="/delete")
	protected String deletedb() {
		CustomerDAO dao=new CustomerDAO();
		dao.printDB();
		return "contents/home";
	}
}
