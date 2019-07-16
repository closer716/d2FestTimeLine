package com.wabu.d2project.post;


import java.util.Date;
import java.text.SimpleDateFormat;
import org.bson.BsonTimestamp;

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
    public Date date;
	
	public Post toEntity() {
		return new Post(userId, contents, date);
	}
    
}
