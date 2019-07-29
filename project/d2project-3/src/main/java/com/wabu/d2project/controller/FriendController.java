package com.wabu.d2project.controller;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wabu.d2project.post.PostService;
import com.wabu.d2project.user.User;
import com.wabu.d2project.user.UserService;
import com.wabu.d2project.user.dataContainer.Friend;
import com.wabu.d2project.user.dataContainer.Notification;
import com.wabu.d2project.util.Constant;
import com.wabu.d2project.util.Util;

@Controller
@RequestMapping(value="/friend")
public class FriendController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private PostService postService;
	private Util util = new Util();
	
	public static int from = 0;
	
	
	@RequestMapping(value="/register")
	public String addFriend(@RequestParam("id") String id, @RequestParam("friendId") String friendId) throws Exception{
		userService.addFriend(new Friend(id, friendId), new Friend(friendId, id));
		return "contents/test";
	}
	
	@RequestMapping(value="/friendList", method=RequestMethod.GET)
	public String friendList(@AuthenticationPrincipal User user, Model model)throws Exception{
		ArrayList<User> friends = userService.getUserTable("id, NAME, sex, birthday, city, school, office", 
				"(SELECT friendId FROM friend WHERE id=\""+user.getId()+"\")fr"+
				" JOIN user AS us ON fr.friendId=us.id limit 10");
		model.addAttribute("friends", friends);
		return "contents/friendList";
	}
	
	@RequestMapping(value="/friendRequest")
	@ResponseBody
	public ResponseEntity<Object> friendRequest(@AuthenticationPrincipal User user, Model model, String friendId, int contents)throws Exception
	{	
		Notification notif = new Notification(user.getId(), friendId, contents, new Date());
		userService.notificationRegister(notif);
		ServiceResponse<List<PostDto>> response = new ServiceResponse<>("success", data);
	    return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/friendSearch", method=RequestMethod.GET)
	protected String friendSearch(@AuthenticationPrincipal User user, @RequestParam("search") String search, Model model) throws Exception{
		ArrayList<User> result = userService.getUserTable("id, name, sex, birthday, city, school, office", "user WHERE id LIKE \"%"+search+"%\" OR name LIKE \"%"+search+"%\"");
		result.get(0).setCity(0);
		
		ArrayList<User> friendsFriend = userService.getFriendsFriend(user.getId(), from, Constant.recommendNum);
		ArrayList<User> mayFriend = userService.getMayFriend(user, from, Constant.recommendNum);
		ArrayList<String> postId = userService.getPostId(user.getId(), user.getRegistrationDate(), from, Constant.pageNum);
		
		model.addAttribute("posts", postService.findBy_id(postId));
		model.addAttribute("friendsFriend", friendsFriend);
		model.addAttribute("mayFriend", mayFriend);
		
		model.addAttribute("searchResult", result);
		return "contents/friendSearch";
	}
	
}
