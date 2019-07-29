package com.wabu.d2project.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.wabu.d2project.post.PostDto;
import com.wabu.d2project.post.PostService;
import com.wabu.d2project.user.User;
import com.wabu.d2project.user.UserService;

@Service
public class Util {
	
	public String generateUserId(){
		int count = (int)(Math.random()*13+8);
		String result = new String();
		String[] valid = new String[] {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
				"0","1","2","3","4","5","6","7","8","9"};
		int length = valid.length;
		for(int i=0 ; i<count ; i++)
			result+=valid[(int)(Math.random()*length)];
		return result;
	}
	public String generatePassword() {
		int count = (int)(Math.random()*8+9);
		String result = new String();
		String[] valid = new String[] {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
				"0","1","2","3","4","5","6","7","8","9","!","@","#","$","%","^","&","*"};
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
	    if(Math.random()*50<1)
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
	public static String makeSuffix(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		return Integer.toString(100*(year-2000)+month);
	}
	
	public static String objectIdtoString(ObjectId id)
	{
		char[] HEX_CHARS = new char[] {
			      '0', '1', '2', '3', '4', '5', '6', '7',
			      '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		char[] chars = new char[24];
		int i = 0;
		for (byte b : id.toByteArray()) {
			chars[i++] = HEX_CHARS[b >> 4 & 0xF];
			if(i==23)
				b += (byte)0x01;
			chars[i++] = HEX_CHARS[b & 0xF];
		}
		return new String(chars);
	}	
	
	public void addPost(String id, String name, String contents, UserService userService, PostService postService) throws Exception{
		Date date = new Date();
		SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String a = formattedDate.format(date);
		PostDto post = new PostDto(ObjectId.get(), id, name, contents,formattedDate.parse(a));
		postService.addPost(post);
		userService.addPost(post);
	}
	
	public boolean verifyId(String id) {
		if(id.length() > 20)
			return false;
		for(int i=0 ; i<id.length() ; i++) {
			char a = id.charAt(i);
			if( a < 80 || a > 122 )
				return false;
			else if( a > 89 && a < 97)
				return false;
		}
		
		return true;
	}
	public boolean verifyPassword(String password) {
		char[] poss = {'!','@','#','$','%','^','&','*'};
		if(password.length()>16)
			return false;
		for(int i=0 ; i<password.length();i++){
			char a = password.charAt(i);
		}
		return true;
	}
}
