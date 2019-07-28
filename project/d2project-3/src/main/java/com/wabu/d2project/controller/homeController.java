package com.wabu.d2project.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wabu.d2project.LoginUserDetailService;
import com.wabu.d2project.Util;
import com.wabu.d2project.post.PostDto;
import com.wabu.d2project.post.PostService;
import com.wabu.d2project.user.User;
import com.wabu.d2project.user.UserService;
import com.wabu.d2project.user.dataContainer.Friend;
import com.wabu.d2project.user.dataContainer.Notification;

@Controller
@RequestMapping(value="/")
public class homeController{
	@Autowired
	private UserService userService;
	@Autowired
	private PostService postService;
	@Autowired
	private LoginUserDetailService loginService;
	private Util util = new Util();
	
	public static final int recommendNum=10;
	public static int from = 0;

	@RequestMapping(value="/timeline")
	protected String home(@AuthenticationPrincipal User user, Model model) throws Exception{
		from = 0;
		ArrayList<User> friendsFriend = userService.getFriendsFriend(user.getId(), from, recommendNum);
		ArrayList<User> mayFriend = userService.getMayFriend(user, from, recommendNum);
		ArrayList<String> postId = userService.getPostId(user.getId(), user.getRegistrationDate(), from, PostController.pageNum);
		
		model.addAttribute("posts", postService.findBy_id(postId));
		model.addAttribute("friendsFriend", friendsFriend);
		model.addAttribute("mayFriend", mayFriend);
		
		return "contents/timeline";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String loginByGet(Model model,HttpServletRequest req){
		model.addAttribute("message",req.getServletContext());
		return "contents/login";
	}
	
	@RequestMapping(value="/loginSuccess", method=RequestMethod.GET)
	public String loginSuccess(HttpServletRequest req){
		return "contents/timeline";	
	}
	@RequestMapping(value="/friendList", method=RequestMethod.GET)
	public String friendList(@AuthenticationPrincipal User user, Model model)throws Exception{
		ArrayList<User> friends = userService.getUserTable("id, NAME, sex, birthday, city, school, office", 
				"(SELECT friendId FROM friend WHERE id=\""+user.getId()+"\")fr"+
				" JOIN user AS us ON fr.friendId=us.id");
		model.addAttribute("friends", friends);
		return "contents/friendList";
	}
	@RequestMapping(value="/please")
	protected String please(@AuthenticationPrincipal User user) {
		System.out.println(user.toString());
		return "contents/test";
	}
	
	@RequestMapping(value="/friendSearch", method=RequestMethod.GET)
	protected String friendSearch(@AuthenticationPrincipal User user, @RequestParam("search") String search, Model model) throws Exception{
		ArrayList<User> result = userService.getUserTable("id, name, sex, birthday, city, school, office", "user WHERE id LIKE \"%"+search+"%\" OR name LIKE \"%"+search+"%\"");
		result.get(0).setCity(0);
		
		ArrayList<User> friendsFriend = userService.getFriendsFriend(user.getId(), from, recommendNum);
		ArrayList<User> mayFriend = userService.getMayFriend(user, from, recommendNum);
		ArrayList<String> postId = userService.getPostId(user.getId(), user.getRegistrationDate(), from, PostController.pageNum);
		
		model.addAttribute("posts", postService.findBy_id(postId));
		model.addAttribute("friendsFriend", friendsFriend);
		model.addAttribute("mayFriend", mayFriend);
		
		model.addAttribute("searchResult", result);
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
    public String query(@RequestParam("id") String id, @RequestParam("password") String password,@RequestParam("sex") boolean sex, @RequestParam("name") String name,
    		@RequestParam("birthday") String birthday, @RequestParam("city") int city, @RequestParam("school") int school, @RequestParam("office") int office
    		) throws Exception{
		User user = new User(id, password, name, sex, birthday, city, school, office, new Date());
        userService.userRegister(loginService.save(user));
        return "contents/test";
    }
	
	/* 포스트 등록 */
	@RequestMapping(value="/addPost", method=RequestMethod.GET)
	public String addPost(@AuthenticationPrincipal User user, Model model, @RequestParam("contents") String contents) throws Exception{
		util.addPost(user.getId(),user.getName(),contents, userService, postService);
		return "contents/timeline";
	}
	
	@RequestMapping(value="/register/friend")
	public String addFriend(@RequestParam("id") String id, @RequestParam("friendId") String friendId) throws Exception{
		userService.addFriend(new Friend(id, friendId), new Friend(friendId, id));
		return "contents/test";
	}
	

	
	
}
