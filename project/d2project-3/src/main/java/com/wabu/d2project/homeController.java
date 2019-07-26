package com.wabu.d2project;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wabu.d2project.post.PostDto;
import com.wabu.d2project.post.PostService;
import com.wabu.d2project.user.DataContainer;
import com.wabu.d2project.user.Friend;
import com.wabu.d2project.user.Notification;
import com.wabu.d2project.user.User;
import com.wabu.d2project.user.UserService;

@Controller
@RequestMapping(value="/")
public class homeController{
	@Autowired
	private UserService userService;
	@Autowired
	private PostService postService;
	private Util util = new Util();

	@RequestMapping(value="/timeline")
	protected String home(Model model) throws Exception{

		model.addAttribute("posts", postService.findAll());

		
		return "contents/timeline";
	}
	
	public DataContainer[] recommend(User userProfile) throws Exception{
		DataContainer[] result=null;

		return result;
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
    public String query(@RequestParam("id") String id, @RequestParam("password") String password,@RequestParam("sex") boolean sex, @RequestParam("name") String name,
    		@RequestParam("birthday") String birthday, @RequestParam("city") int city, @RequestParam("school") int school, @RequestParam("office") int office
    		) throws Exception{
        userService.userRegister(new User(id, password, name, sex, birthday, city, school, office, new Date()));
        return "contents/test";
    }
	
	/* 포스트 등록 */
	@RequestMapping("register/post")
    public String registerPost(@RequestParam("id") String id, @RequestParam("contents") String contents) throws Exception{
		addPost(id, contents);
		System.out.println("registing poster is complete");
		System.out.println("======================================================");
        return "contents/test";
    }

	
	@RequestMapping(value="/register/friend")
	public String addFriend(@RequestParam("id") String id, @RequestParam("friendId") String friendId) throws Exception{
		userService.addFriend(new Friend(id, friendId), new Friend(friendId, id));
		return "contents/test";
	}
	
	@RequestMapping(value="/generateTestCases")
	public String generateTestCases() throws Exception{
		deleteAllMariaDB();
		postService.deleteAll();
		userService.createTable();
		int userNum=40;
		int partnerNum=400;
		int notificationNum=20;
		int postNum=1;
		registerUser(userNum);
		createFriend(partnerNum);
		createNotification(notificationNum);
		createPosts(postNum);
		//util.postService.deleteAll();
		//util.deleteAllMariaDB();
		return "contents/test";
	}
	public void registerUser(int num) throws Exception{
		for(int i=0 ; i<num ; i++) {
			String id = util.generateUserId();
			userService.userRegister(new User(id , util.generatePassword(), util.generateKoreanName(), true,util.generateBirthday(), 
							(int)(Math.random()*100+1),(int)(Math.random()*300+1),(int)(Math.random()*500+1), new Date()));
		}
		System.out.println("Registing users is completed");
		System.out.println("======================================================");
	}
	
	public void createPosts(int num)throws Exception{
		User[] user = getAllUserId();
		for(int i=0 ; i<num ; i++){
			int a=(int)(Math.random()*user.length);
			addPost(user[a].getId(), util.generatePostContent());
		}
		System.out.println("Creating posts is completed");
		System.out.println("======================================================");
	}

	public void createFriend(int num) throws Exception{
		/* using column1(userId)*/
		User[] user = getAllUserId();
		
		for(int i=0 ; i<num ;i++) {
			int a=(int)(Math.random()*user.length);
			int b=(int)(Math.random()*user.length);
			Friend[] tmp = userService.getFriendTable("id, friendId", "friend_"+user[a].getId().substring(0,UserService.frontIdIndex)+ 
					" WHERE id=\""+user[a].getId()+"\""+" AND id=\""+user[b].getId()+"\"");
			if(a==b || tmp.length!=0) {
				i--;
				continue;
			}
			userService.addFriend(new Friend(user[a].getId(), user[b].getId()), new Friend(user[b].getId(), user[a].getId()));
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
		User[] user = getAllUserId();

		for(int i=0 ; i<num ;i++) {
			int a=(int)(Math.random()*user.length);
			int b=(int)(Math.random()*user.length);
			if(a==b){
				i--; 
				continue;
			}else if(userService.isFriend(user[a].getId(), user[b].getId())) {
				userService.notificationRegister(new Notification(user[a].getId(),user[b].getId(), 1, new Date()));
			}
			else
				userService.notificationRegister(new Notification(user[a].getId(),user[b].getId(), 0, new Date()));
		}
		System.out.println("Creating norifications is completed");
		System.out.println("======================================================");
	}
	public void addPost(String id, String contents) throws Exception{
		Date date = new Date();
		SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String a = formattedDate.format(date);
		PostDto post = new PostDto(ObjectId.get(), id, contents,formattedDate.parse(a));
		postService.addPost(post);
		userService.addPost(post);
	}
	
	public User[] getAllUserId() throws Exception{
		String str="(SELECT id FROM user_a";
		for(int i=98; i<123 ; i++){
			str+=" union SELECT id FROM user_"+Character.toString((char)i);
		}
		for(int i=48; i<58; i++){
			str+=" union SELECT id FROM user_"+Character.toString((char)i);
		}
		str+=")p";
		return userService.getUserTable("id", str );
	}

}
