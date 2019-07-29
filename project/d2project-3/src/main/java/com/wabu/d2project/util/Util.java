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
		String[] lastName = new String[]{"김", "이", "박", "최", "정", "강", "조", "윤", "장", "임", "한", "오", "서", "신", "권", "황", "안",
	            "송", "류", "전", "홍", "고", "문", "양", "손", "배", "조", "백", "허", "유", "남", "심", "노", "정", "하", "곽", "성", "차", "주",
	            "우", "구", "신", "임", "나", "전", "민", "유", "진", "지", "엄", "채", "원", "천", "방", "공", "강", "현", "함", "변", "염", "양",
	            "변", "여", "추", "노", "도", "소", "신", "석", "선", "설", "마", "길", "주", "연", "방", "위", "표", "명", "기", "반", "왕", "금",
	            "옥", "육", "인", "맹", "제", "모", "장", "남", "탁", "국", "여", "진", "어", "은", "편", "구", "용"};
	            String[] firstName = new String[]{"가", "강", "건", "경", "고", "관", "광", "구", "규", "근", "기", "길", "나", "남", "노", "누", "다",
	            "단", "달", "담", "대", "덕", "도", "동", "두", "라", "래", "로", "루", "리", "마", "만", "명", "무", "문", "미", "민", "바", "박",
	            "백", "범", "별", "병", "보", "빛", "사", "산", "상", "새", "서", "석", "선", "설", "섭", "성", "세", "소", "솔", "수", "숙", "순",
	            "숭", "슬", "승", "시", "신", "아", "안", "애", "엄", "여", "연", "영", "예", "오", "옥", "완", "요", "용", "우", "원", "월", "위",
	            "유", "윤", "율", "으", "은", "의", "이", "익", "인", "일", "잎", "자", "잔", "장", "재", "전", "정", "제", "조", "종", "주", "준",
	            "중", "지", "진", "찬", "창", "채", "천", "철", "초", "춘", "충", "치", "탐", "태", "택", "판", "하", "한", "해", "혁", "현", "형",
	            "혜", "호", "홍", "화", "환", "회", "효", "훈", "휘", "희", "운", "모", "배", "부", "림", "봉", "혼", "황", "량", "린", "을", "비",
	            "솜", "공", "면", "탁", "온", "디", "항", "후", "려", "균", "묵", "송", "욱", "휴", "언", "령", "섬", "들", "견", "추", "걸", "삼",
	            "열", "웅", "분", "변", "양", "출", "타", "흥", "겸", "곤", "번", "식", "란", "더", "손", "술", "훔", "반", "빈", "실", "직", "흠",
	            "흔", "악", "람", "뜸", "권", "복", "심", "헌", "엽", "학", "개", "롱", "평", "늘", "늬", "랑", "얀", "향", "울", "련"};
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
