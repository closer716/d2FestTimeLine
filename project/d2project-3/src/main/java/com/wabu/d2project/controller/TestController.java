package com.wabu.d2project.controller;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wabu.d2project.LoginUserDetailService;
import com.wabu.d2project.post.PostService;
import com.wabu.d2project.user.User;
import com.wabu.d2project.user.UserService;
import com.wabu.d2project.user.dataContainer.Friend;
import com.wabu.d2project.user.dataContainer.Notification;
import com.wabu.d2project.util.Util;

@Controller
@RequestMapping(value="/test")
public class TestController {
	@Autowired
	private UserService userService;
	@Autowired
	private PostService postService;
	@Autowired
	private LoginUserDetailService loginService;
	private Util util = new Util();
	
	
	@RequestMapping(value="/generateTestCases")
	public String generateTestCases() throws Exception{
		//deleteAllMariaDB();
		//postService.deleteAll();
		//userService.createTable();
		int userNum= 50;
		int partnerNum= 2000;
		int notificationNum=100;
		int postNum= 1000;
		//User user = new User("yoon3784", "1234", "È«¼®À±", false, "1995-06-01", 1 , 1, 1, new Date());
        //userService.userRegister(loginService.save(user));
		registerUser(userNum);
		createFriend(partnerNum);
		createNotification(notificationNum);
		createPosts(postNum);
		//util.postService.deleteAll();
		//util.deleteAllMariaDB();
		return "contents/test";
	}
	
/* Local functions to make test case */
	
	public void registerUser(int num) throws Exception{
		for(int i=0 ; i<num ; i++) {
			String id = util.generateUserId(); 
			if(userService.getUserById(id)!=null) {i--;continue;}
			User user = new User(id , util.generatePassword(), util.generateKoreanName(), true,util.generateBirthday(), 
					(int)(Math.random()*100+1),(int)(Math.random()*300+1),(int)(Math.random()*500+1), new Date());
			loginService.save(user);
			userService.userRegister(user);
			if(i%100 == 0)
				System.out.println("registering now "+i);
		}
		System.out.println("Registing users is completed");
		System.out.println("======================================================");
	}
	
	public void createPosts(int num)throws Exception{
		ArrayList<User> user = userService.getUserTable("id, name", "user");
		for(int i=0 ; i<num ; i++){
			int a=(int)(Math.random()*user.size());
			util.addPost(user.get(a).getId(), user.get(a).getName(), util.generatePostContent(), userService, postService);
			if(i%500 == 0)
				System.out.println("creating posts now "+i);
		}
		System.out.println("Creating posts is completed");
		System.out.println("======================================================");
	}

	public void createFriend(int num) throws Exception{
		/* using column1(userId)*/
		ArrayList<User> user = userService.getUserTable("id", "user");
		
		for(int i=0 ; i<num ;i++) {
			int a=(int)(Math.random()*user.size());
			int b=(int)(Math.random()*user.size());
			ArrayList<Friend> tmp = userService.getFriendTable("id, friendId", "friend WHERE (id=\""+user.get(a).getId()+"\"" +
															" AND friendId=\""+user.get(b).getId()+"\")");
			if(a==b || tmp.size()>0) {
				i--;
				continue;
			}
			userService.addFriend(new Friend(user.get(a).getId(), user.get(b).getId()), new Friend(user.get(b).getId(), user.get(a).getId()));
			if(i%500 == 0)
				System.out.println("creating now "+i);
			
		}
		System.out.println("Creating friends is completed");
		System.out.println("======================================================");
	}
	
	public void deleteAllMariaDB() throws Exception{
		userService.dropAllTable();
		System.out.println("Deleting all user of mariadb is completed");
		System.out.println("======================================================");
	}
	
	public void createNotification(int num) throws Exception{
		ArrayList<User> user = userService.getUserTable("id", "user");

		for(int i=0 ; i<num ;i++) {
			int a=(int)(Math.random()*user.size());
			int b=(int)(Math.random()*user.size());
			if(a==b){
				i--; 
				continue;
			}else if(userService.isFriend(user.get(a).getId(), user.get(b).getId())) {
				userService.notificationRegister(new Notification(user.get(a).getId(),user.get(b).getId(), 1, new Date()));
			}
			else
				userService.notificationRegister(new Notification(user.get(a).getId(),user.get(b).getId(), 0, new Date()));
			if(i%100 == 0)
				System.out.println("Creating notification now "+i);
		}
		System.out.println("Creating nortfications is completed");
		System.out.println("======================================================");
	}
}
