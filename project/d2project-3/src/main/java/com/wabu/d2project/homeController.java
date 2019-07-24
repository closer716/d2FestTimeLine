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
import com.wabu.d2project.user.Friend;
import com.wabu.d2project.user.DataContainer;
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
			 friendsFriend = userService.getFriendsFriend(profile[i++].getId());
		}while(friendsFriend == null);
		DataContainer[] recommend = new DataContainer[profile.length];
		for(int k=1 ; i<profile.length ; k++)
		{
			
		}
		model.addAttribute("posts", postService.findAll());
		model.addAttribute("recommend", friendsFriend);
		
		return "contents/timeline";
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
    		@RequestParam("birthday") String birthday, @RequestParam("country") String country, @RequestParam("city") String city, 
    		@RequestParam("elmSchool") String elmSchool, @RequestParam("midSchool") String midSchool, @RequestParam("highSchool") String highSchool, @RequestParam("univSchool") String univSchool,
    		@RequestParam("office") String office) throws Exception{
        userService.register(new User(id, password), new Profile(id, name, sex, birthday, country, city, elmSchool, midSchool, highSchool, univSchool, office));
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
		//deleteAllMariaDB();
		postService.deleteAll();
		//userService.createUserTable();
		//userService.createProfileTable();
		int userNum=20;
		int partnerNum=50;
		int notificationNum=5;
		int postNum=5;
		//registerUser(userNum);
		//createFriend(partnerNum);
		//createNotification(notificationNum);
		createPosts(postNum);
		//postService.deleteAll();
		//deleteAllMariaDB();
		return "contents/test";
	}
	
	private void registerUser(int num) throws Exception{
		Functions caseGenerator = new Functions();
		for(int i=0 ; i<num ; i++) {
			String id = caseGenerator.generateUserId();
			userService.register(new User(id , caseGenerator.generatePassword()), 
					new Profile(id, caseGenerator.generateKoreanName(), true,caseGenerator.generateBirthday(), 
							caseGenerator.generateCountry(),"null","null","null","null","null","null"));
		}
		System.out.println("Registing users is completed");
		System.out.println("======================================================");
	}
	
	private void createPosts(int num)throws Exception{
		Functions caseGenerator = new Functions();
		/* using column1(userId)*/
		DataContainer[] userId = userService.getUserId();
		for(int i=0 ; i<num ; i++){
			int a=(int)(Math.random()*userId.length);
			ObjectId id = ObjectId.get();
			postService.addPost(new PostDto(id, userId[a].getColumn1(), caseGenerator.generatePostContent(), new Date()));
			registerPostAtTable(userId[a].getColumn1(), id.toString());
		}
		System.out.println("Creating posts is completed");
		System.out.println("======================================================");
	}
	
	private void registerPostAtTable(String userId, String postId) throws Exception {
		userService.postRegister("posts_"+userId, postId);
		/* using column1(friendId)*/
		DataContainer[] friendsId = userService.getFriendsId(userId);
		for(int i=0 ; i<friendsId.length ; i++) {
			userService.postRegister("posts_"+friendsId[i].getColumn1(), postId);
		}
	}

	private void createFriend(int num) throws Exception{
		/* using column1(userId)*/
		DataContainer[] userId = userService.getUserId();
		
		for(int i=0 ; i<num ;i++) {
			int a=(int)(Math.random()*userId.length);
			int b=(int)(Math.random()*userId.length);
			if(a==b || userService.selectFromTableWhere(userId[a].getColumn1(), userId[b].getColumn1()).length != 0) {
				i--; 
				continue;
			}
			userService.addFriend(userId[a].getColumn1(), userId[b].getColumn1());
		}
		System.out.println("Creating friends is completed");
		System.out.println("======================================================");
	}
	
	private void deleteAllMariaDB() throws Exception{
		/* using column1(userId)*/
		DataContainer[] userId = userService.getUserId();
		for(int i=0; i< userId.length; i++) { 
			userService.deleteUser(userId[i].getColumn1());
		}
		userService.dropUsreAndProfileTable();
		System.out.println("Deleting all user of mariadb is completed");
		System.out.println("======================================================");
	}
	
	private void createNotification(int num) throws Exception{
		/* using column1(userId)*/
		DataContainer[] userId = userService.getUserId();
		
		for(int i=0 ; i<num ;i++) {
			int a=(int)(Math.random()*userId.length);
			int b=(int)(Math.random()*userId.length);
			if(a==b){
				i--; 
				continue;
			}else if(userService.selectFromTableWhere(userId[a].getColumn1(), userId[b].getColumn1()).length != 0) {
				userService.notificationRegister(userId[a].getColumn1(),userId[b].getColumn1(), 1);
			}
			else
				userService.notificationRegister(userId[a].getColumn1(),userId[b].getColumn1(), 0);
		}
		System.out.println("Creating norifications is completed");
		System.out.println("======================================================");
	}

}
