package com.wabu.d2project.post;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostDto {
	private ObjectId id;
	private String userId;
	private String name;
	private String contents;
	private Date date;
	private String postDate;
	
	public String toString() {
	        return String.format(
	                "post[id=%s, userId=%s, contents='%s', date='%s']",
	                id, userId, contents, date);
	}
	public Post toEntity() {
		return new Post(ObjectId.get(), name, userId, contents, date);
	}
	
	public void setPostDate() {
		SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
		String postDate = formattedDate.format(date);
		this.postDate=postDate;
	}
	
	public PostDto(ObjectId id, String userId, String name, String contents, Date date){
		SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
		String postDate = formattedDate.format(date);
		this.id=id;
		this.userId=userId;
		this.name=name;
		this.contents=contents;
		this.date=date;
		this.postDate=postDate;
	}
	public PostDto(ObjectId id, String userId, String name, String contents, Date date, String postDate){
		SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
		String strPostDate = formattedDate.format(date);
		this.id=id;
		this.userId=userId;
		this.name=name;
		this.contents=contents;
		this.date=date;
		this.postDate=strPostDate;
	}
}
