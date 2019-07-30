package com.wabu.d2project.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wabu.d2project.LoginUserDetailService;
import com.wabu.d2project.ServiceResponse;
import com.wabu.d2project.post.PostDto;
import com.wabu.d2project.post.PostService;
import com.wabu.d2project.user.User;
import com.wabu.d2project.user.UserService;
import com.wabu.d2project.user.dataContainer.Notification;
import com.wabu.d2project.util.Constant;

@Controller
@RequestMapping(value="/")
public class homeController{
	@Autowired
	private UserService userService;
	@Autowired
	private PostService postService;
	@Autowired
	private LoginUserDetailService loginService;

	@RequestMapping(value="/timeline")
	protected String home(@AuthenticationPrincipal User user, Model model) throws Exception{
		ArrayList<User> friendsFriend = userService.getFriendsFriend(user.getId(), 0, Constant.recommendNum);
		ArrayList<User> mayFriend = userService.getMayFriend(user, 0, Constant.recommendNum);
		ArrayList<String> postId = userService.getPostId(user.getId(), user.getRegistrationDate(), 0, Constant.pageNum);
		ArrayList<Notification> notification = userService.getNotificationTable("user.name AS id, notificationId, content, friendId", "(SELECT friendId, notificationId, content, date FROM "
				+ "notification where id=\""+user.getId()+"\") as result JOIN user WHERE result.friendId = user.id ORDER BY result.date desc limit "+Constant.notificationNum);
		ArrayList<PostDto> post= postService.findBy_id(postId);
		
		for(int i=0 ; i<post.size(); i++) {
			post.get(i).setPostDate();
		}
		
		for(int i=0 ; i<mayFriend.size(); i++) {
			if(mayFriend.get(i).getCity()!=user.getCity())
				mayFriend.get(i).setCity(0);
			if(mayFriend.get(i).getSchool()!=user.getSchool())
				mayFriend.get(i).setSchool(0);
			if(mayFriend.get(i).getOffice()!=user.getOffice())
				mayFriend.get(i).setOffice(0);
		}
		
		model.addAttribute("posts", post);
		model.addAttribute("notification", notification);
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
		String result="";
		if(user==null)
			result="0";
		else
			result="1";
			
		ServiceResponse<String> response = new ServiceResponse<>("success", result);
	    return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
	@PostMapping("register/confirm")
    public String query(@RequestParam("id") String id, @RequestParam("password") String password, @RequestParam("name") String name,
    		@RequestParam("birthday") String birthday, @RequestParam("city") int city, @RequestParam("school") int school, @RequestParam("office") int office
    		) throws Exception{
		User user = new User(id, password, name, birthday, city, school, office, new Date());
        userService.userRegister(loginService.save(user));
        return "contents/login";
    }
}
