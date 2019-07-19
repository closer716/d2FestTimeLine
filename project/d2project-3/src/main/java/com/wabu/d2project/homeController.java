package com.wabu.d2project;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    public String query(@RequestParam("userId") String userId, @RequestParam("password") String password, @RequestParam("name") String name,
    		@RequestParam("birthday") Date birthday) throws Exception{
        userService.register(userId, name, password, birthday, "korea");
        return "contents/test";
    }
	
	/* 포스트 등록 */
	@RequestMapping("register/post")
    public String query(@RequestParam("userId") String userId, @RequestParam("contents") String contents) throws Exception{
		Date date = new Date();
		SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String a = formattedDate.format(date);
		Date to = formattedDate.parse(a);
		postService.addPost(new PostDto("userId","contents",to));
        return "contents/test";
    }
	
	@RequestMapping(value="/register/friend")
	public String friend() throws Exception{
		userService.addFriend("yoon3784", "un3784");
		return "contents/test";
	}
	
	@RequestMapping(value="/generateTestCases")
	public String generateTestCases() throws Exception{
		int userNum=20;
		int partnerNum=100;
		registerUser(userNum);
		createFriend(partnerNum);
		//deleteAll();
		return "contents/test";
	}
	
	public void registerUser(int num) throws Exception{
		Functions caseGenerator = new Functions();
		for(int i=0 ; i<num ; i++) {
			userService.register(caseGenerator.generateUserId(), caseGenerator.generateKoreanName(), caseGenerator.generatePassword(), 
					caseGenerator.generateBirthday(), caseGenerator.generateCountry());
		}
	}

	public void createFriend(int num) throws Exception{
		String[] userId = userService.getUserId();
		
		for(int i=0 ; i<num ;i++) {
			int a=(int)(Math.random()*userId.length);
			int b=(int)(Math.random()*userId.length);
			if(a==b || userService.selectFromTableWhere(userId[a], userId[b]).length != 0) {
				i--; 
				continue;
			}
			userService.addFriend(userId[a], userId[b]);
		}
	}
	
	public void deleteAll() throws Exception{
		String[] userId = userService.getUserId();
		for(int i=0; i< userId.length; i++) { 
			userService.deleteUser(userId[i]);
		}
	}
	
}
