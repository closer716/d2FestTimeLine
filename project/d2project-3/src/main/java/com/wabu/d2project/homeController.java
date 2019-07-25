package com.wabu.d2project;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import com.wabu.d2project.user.Profile;
import com.wabu.d2project.user.User;
import com.wabu.d2project.user.UserService;

@Controller
@RequestMapping(value="/")
public class homeController{
	@Autowired
	private UserService userService;
	@Autowired
	private PostService postService;

	@RequestMapping(value="/timeline")
	protected String home(Model model) throws Exception{
		/* using column1(userId)*/
		Profile[] profile = userService.getProfleTable("*");
		/* using column1(friendId), column2(count(friendId)) */
		DataContainer[] friendsFriend=null;
		int i=0;
		do {
			 friendsFriend = userService.getFriendsFriend(profile[i++].getId(),0, 5);
		}while(friendsFriend == null);
		DataContainer[] recommend=recommend(profile[i-1]);
		System.out.println(profile.length+"  "+friendsFriend.length);

		model.addAttribute("posts", postService.findAll());
		model.addAttribute("friendsFriend", friendsFriend);
		model.addAttribute("recommend", recommend);
		
		return "contents/timeline";
	}
	
	public DataContainer[] recommend(Profile userProfile) throws Exception{
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
        userService.register(new User(id, password), new Profile(id, name, sex, birthday, city, school, office));
        return "contents/test";
    }
	
	/* 포스트 등록 */
	@RequestMapping("register/post")
    public String query(@RequestParam("userId") String userId, @RequestParam("contents") String contents) throws Exception{
		Date date = new Date();
		SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String a = formattedDate.format(date);
		Date to = formattedDate.parse(a);
		postService.addPost(new PostDto(ObjectId.get(), "userId","contents",to));
        return "contents/test";
    }
	
	@RequestMapping(value="/register/friend")
	public String friend() throws Exception{
		userService.addFriend("yoon3784", "un3784");
		return "contents/test";
	}
	
	@RequestMapping(value="/generateTestCases")
	public String generateTestCases() throws Exception{
		Util util = new Util();
		//util.deleteAllMariaDB();
		//util.postService.deleteAll();
		//util.userService.createUserTable();
		//util.userService.createProfileTable();
		int userNum=10;
		int partnerNum=20;
		int notificationNum=20;
		int postNum=5;
		util.registerUser(userNum);
		util.createFriend(partnerNum);
		util.createNotification(notificationNum);
		util.createPosts(postNum);
		//util.postService.deleteAll();
		//util.deleteAllMariaDB();
		return "contents/test";
	}
	


}
