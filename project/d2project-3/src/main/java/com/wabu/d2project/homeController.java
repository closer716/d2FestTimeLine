package com.wabu.d2project;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bson.BsonTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wabu.d2project.post.PostDto;
import com.wabu.d2project.post.PostService;
import com.wabu.d2project.user.UserService;

@Controller
@RequestMapping(value="/")
public class homeController{
	@Autowired
	private UserService userService;
	@Autowired
	private PostService postService;

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
        userService.register(user_id, user_name, user_password, birthday);
        return "contents/test";
    }
	
	/* 포스트 등록 */
	@PostMapping("register/post")
    public String query(@RequestParam("user_id") String user_id, @RequestParam("contents") String user_password) throws Exception{
		Date date = new Date();
		postService.addPost(new PostDto("user_id","contents",date));
        return "contents/test";
    }
	
	/* 포스트 프린트 */
	@RequestMapping(value="/test")
	public String test() {
		postService.printDB();
		return "contents/test";
	}
	
	
}
