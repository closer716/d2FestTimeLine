package com.wabu.d2project;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wabu.d2project.post.PostDto;
import com.wabu.d2project.post.PostService;
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
	
	public static final int pageNum=10;
	public static final int recommendNum=10;

	@RequestMapping(value="/timeline")
	protected String home(Model model) throws Exception{
		
		User user=userService.getUserById("3ny7nktgjibq3");
		int from = 0;
		ArrayList<User> friendsFriend = userService.getFriendsFriend(user.getId(), from, recommendNum);
		ArrayList<User> mayFriend = userService.getMayFriend(user, from, recommendNum);
		ArrayList<String> postId = userService.getPostId(user.getId(), user.getRegistrationDate(), from, pageNum);
		
		model.addAttribute("posts", postService.findBy_id(postId));
		model.addAttribute("friendsFriend", friendsFriend);
		model.addAttribute("mayFriend", mayFriend);
		
		return "contents/timeline";
	}
	
	@RequestMapping(value="/login")
	protected String login(@AuthenticationPrincipal LoginDetails userDetails) {
		return "contents/login";
	}
	
	@RequestMapping(value="/please")
	protected String please( ) {
		return "contents/test";
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
    public String registerPost(@RequestParam("id") String id,@RequestParam("name") String name, @RequestParam("contents") String contents) throws Exception{
		addPost(id, name,contents);
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
		//deleteAllMariaDB();
		//postService.deleteAll();
		userService.createTable();
		int userNum= 1000;
		int partnerNum=30000;
		int notificationNum=3000;
		int postNum=1000;
		//registerUser(userNum);
		//createFriend(partnerNum);
		//createNotification(notificationNum);
		//createPosts(postNum);
		//util.postService.deleteAll();
		//util.deleteAllMariaDB();
		return "contents/test";
	}
	
	public void addPost(String id, String name, String contents) throws Exception{
		Date date = new Date();
		SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String a = formattedDate.format(date);
		PostDto post = new PostDto(ObjectId.get(), id, name, contents,formattedDate.parse(a));
		postService.addPost(post);
		userService.addPost(post);
	}
	
	/* Local functions to make test case */
	
	public void registerUser(int num) throws Exception{
		for(int i=0 ; i<num ; i++) {
			String id = util.generateUserId();
			userService.userRegister(new User(id , util.generatePassword(), util.generateKoreanName(), true,util.generateBirthday(), 
							(int)(Math.random()*100+1),(int)(Math.random()*300+1),(int)(Math.random()*500+1), new Date()));
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
			addPost(user.get(a).getId(), user.get(a).getName(),util.generatePostContent());
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
