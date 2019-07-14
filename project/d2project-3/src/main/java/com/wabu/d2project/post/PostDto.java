package com.wabu.d2project.post;


import java.text.SimpleDateFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
	@NotBlank
	public String userId;
	@NotBlank
    public String contents;
	@NotBlank
    public SimpleDateFormat date;
	
	public PostDto(String userId, String contents, SimpleDateFormat date){
		this.userId = userId;
	    this.contents = contents;
	    this.date = date;
	}
	
	
	public Post toEntity() {
		return new Post(userId, contents, date);
	}
    
}
