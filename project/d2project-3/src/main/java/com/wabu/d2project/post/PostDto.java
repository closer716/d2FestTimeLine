package com.wabu.d2project.post;


import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
	public ObjectId id;
	public String userId;
	public String name;
    public String contents;
    public Date date;
	
	public String toString() {
	        return String.format(
	                "post[id=%s, userId=%s, contents='%s', date='%s']",
	                id, userId, contents, date);
	}
	public Post toEntity() {
		return new Post(ObjectId.get(), name, userId, contents, date);
	}
    
}
