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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.wabu.d2project.ServiceResponse;
import com.wabu.d2project.post.PostDto;
import com.wabu.d2project.post.PostService;
import com.wabu.d2project.user.User;
import com.wabu.d2project.user.UserService;
import com.wabu.d2project.user.dataContainer.Friend;
import com.wabu.d2project.user.dataContainer.Notification;
import com.wabu.d2project.util.Constant;
import com.wabu.d2project.util.Util;

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
		ArrayList<User> friendsFriend = userService.getFriendsFriend(user.getId(), 0, recommendNum);
		ArrayList<User> mayFriend = userService.getMayFriend(user, 0, recommendNum);
		ArrayList<String> postId = userService.getPostId(user.getId(), user.getRegistrationDate(), 0, Constant.pageNum);
		ArrayList<Notification> notification = userService.getNotificationTable("user.name AS id, notificationId, content, friendId", "(SELECT friendId, notificationId, content, date FROM "
				+ "notification where id=\""+user.getId()+"\") as result JOIN user WHERE result.friendId = user.id ORDER BY result.date desc limit 5");
		
		model.addAttribute("posts", postService.findBy_id(postId));
		model.addAttribute("friendsFriend", friendsFriend);
		model.addAttribute("mayFriend", mayFriend);
		model.addAttribute("notification", notification);
		
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

	@RequestMapping(value="/profile")
	protected String profile() {
		return "contents/profile";
	}
	
	@RequestMapping(value="/register")
	protected String register() { 
		return "contents/register";
	}
	
	@RequestMapping(value="/register/duplicate")
	public ResponseEntity<Object> duplicate(@RequestParam("id") String id) throws Exception{
		User user = userService.getUserById(id);
		ServiceResponse<User> response = new ServiceResponse<>("success", user);
	    return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
	@PostMapping("register/confirm")
    public String query(@RequestParam("id") String id, @RequestParam("password") String password,@RequestParam("sex") boolean sex, @RequestParam("name") String name,
    		@RequestParam("birthday") String birthday, @RequestParam("city") int city, @RequestParam("school") int school, @RequestParam("office") int office
    		) throws Exception{
		User user = new User(id, password, name, sex, birthday, city, school, office, new Date());
        userService.userRegister(loginService.save(user));
        return "contents/test";
    }
}
