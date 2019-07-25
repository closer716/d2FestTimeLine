package com.wabu.d2project;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wabu.d2project.post.PostDto;
import com.wabu.d2project.post.PostService;
import com.wabu.d2project.user.DataContainer;
import com.wabu.d2project.user.Profile;
import com.wabu.d2project.user.User;
import com.wabu.d2project.user.UserService;

@Component
public class Util {
	
	@Autowired
	UserService userService;
	@Autowired
	PostService postService;
	
	public String generateUserId(){
		int count = (int)(Math.random()*13+8);
		String userId = RandomStringUtils.randomAlphanumeric(count);
	    return userId;
	}
	public String generatePassword() {
		int count = (int)(Math.random()*8+9);
		String result = new String();
		String[] valid = new String[] {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
				"1","2","3","4","5","6","7","8","9","!","@","#","$","%","^","&","*"};
		int length = valid.length;
		for(int i=0 ; i<count ; i++)
			result+=valid[(int)(Math.random()*length)];
		return result;
	}
	public String generateBirthday() throws Exception{
		String result=new String();
		int year = (int)(Math.random()*120+1899);
		result+=Integer.toString(year);
		int month = (int)(Math.random()*12+1);
		if(month<10)
			result+="0"+Integer.toString(month);
		else
			result+=Integer.toString(month);
		int date = (int)(Math.random()*30+1);
		if(date<10)
			return result+="0"+Integer.toString(date);
		else if(date>28 && month==2)
			date-=3;
		else if(date==31 && (month==1 || month==3 || month==5 ||month==7 || month==8 || month==10 || month==12))
			date--;

		return result+=Integer.toString(date);
	}
	
	
	public String generateKoreanName() {
		String[] lastName = new String[]{"��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "Ȳ", "��",
	            "��", "��", "��", "ȫ", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
	            "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "ä", "��", "õ", "��", "��", "��", "��", "��", "��", "��", "��",
	            "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "ǥ", "��", "��", "��", "��", "��",
	            "��", "��", "��", "��", "��", "��", "��", "��", "Ź", "��", "��", "��", "��", "��", "��", "��", "��"};
	            String[] firstName = new String[]{"��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
	            "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
	            "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
	            "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
	            "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
	            "��", "��", "��", "��", "â", "ä", "õ", "ö", "��", "��", "��", "ġ", "Ž", "��", "��", "��", "��", "��", "��", "��", "��", "��",
	            "��", "ȣ", "ȫ", "ȭ", "ȯ", "ȸ", "ȿ", "��", "��", "��", "��", "��", "��", "��", "��", "��", "ȥ", "Ȳ", "��", "��", "��", "��",
	            "��", "��", "��", "Ź", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
	            "��", "��", "��", "��", "��", "��", "Ÿ", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
	            "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��"};
	    if(Math.random()*20<1)
	    	return lastName[(int)(Math.random()*lastName.length)] + firstName[(int)(Math.random()*firstName.length)] + 
	    			firstName[(int)(Math.random()*firstName.length)] + firstName[(int)(Math.random()*firstName.length)];
	    else
	    	return lastName[(int)(Math.random()*lastName.length)] + firstName[(int)(Math.random()*firstName.length)] + firstName[(int)(Math.random()*firstName.length)];
	}

	public String generatePostContent() {
		int count = (int)(Math.random()*900+100);
		String content = RandomStringUtils.random(count);
		return content;
	}
	
	public static String makeValues(String[] str) {
		String result = new String();
		int i = 0;
		while(true) {
			if(str[i]=="null")
				result+=str[i++];
			else
				result+="\""+str[i++]+"\"";
			if(i==str.length)
				break;
			result+=", ";
		}
		return result;
	}
	
	public void registerUser(int num) throws Exception{
		for(int i=0 ; i<num ; i++) {
			String id = generateUserId();
			userService.register(new User(id , generatePassword()), 
					new Profile(id, generateKoreanName(), true,generateBirthday(), 
							(int)(Math.random()*100+1),(int)(Math.random()*300+1),(int)(Math.random()*500+1)));
		}
		System.out.println("Registing users is completed");
		System.out.println("======================================================");
	}
	
	public void createPosts(int num)throws Exception{
		/* using column1(userId)*/
		DataContainer[] userId = userService.getUserId();
		for(int i=0 ; i<num ; i++){
			int a=(int)(Math.random()*userId.length);
			ObjectId id = ObjectId.get();
			postService.addPost(new PostDto(id, userId[a].getColumn1(), generatePostContent(), new Date()));
			registerPostAtTable(userId[a].getColumn1(), id.toString());
		}
		System.out.println("Creating posts is completed");
		System.out.println("======================================================");
	}
	
	public void registerPostAtTable(String userId, String postId) throws Exception {
		userService.postRegister("posts_"+userId, postId);
		/* using column1(friendId)*/
		DataContainer[] friendsId = userService.getFriendsId(userId);
		for(int i=0 ; i<friendsId.length ; i++) {
			userService.postRegister("posts_"+friendsId[i].getColumn1(), postId);
		}
	}

	public void createFriend(int num) throws Exception{
		/* using column1(userId)*/
		DataContainer[] userId = userService.getUserId();
		
		for(int i=0 ; i<num ;i++) {
			int a=(int)(Math.random()*userId.length);
			int b=(int)(Math.random()*userId.length);
			if(a==b || userService.isFriend(userId[a].getColumn1(), userId[b].getColumn1())) {
				i--; 
				continue;
			}
			userService.addFriend(userId[a].getColumn1(), userId[b].getColumn1());
		}
		System.out.println("Creating friends is completed");
		System.out.println("======================================================");
	}
	
	public void deleteAllMariaDB() throws Exception{
		/* using column1(userId)*/
		DataContainer[] userId = userService.getUserId();
		for(int i=0; i< userId.length; i++) { 
			userService.deleteUser(userId[i].getColumn1());
		}
		userService.dropUsreAndProfileTable();
		System.out.println("Deleting all user of mariadb is completed");
		System.out.println("======================================================");
	}
	
	public void createNotification(int num) throws Exception{
		/* using column1(userId)*/
		DataContainer[] userId = userService.getUserId();
		
		for(int i=0 ; i<num ;i++) {
			int a=(int)(Math.random()*userId.length);
			int b=(int)(Math.random()*userId.length);
			if(a==b){
				i--; 
				continue;
			}else if(userService.isFriend(userId[a].getColumn1(), userId[b].getColumn1())) {
				userService.notificationRegister(userId[a].getColumn1(),userId[b].getColumn1(), 1);
			}
			else
				userService.notificationRegister(userId[a].getColumn1(),userId[b].getColumn1(), 0);
		}
		System.out.println("Creating norifications is completed");
		System.out.println("======================================================");
	}
}
