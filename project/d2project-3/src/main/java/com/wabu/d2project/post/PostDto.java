package com.wabu.d2project.post;


import java.util.Date;
import java.text.SimpleDateFormat;
import org.bson.BsonTimestamp;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
	public ObjectId id;
	@NotBlank
	public String userId;
	@NotBlank
    public String contents;
	@NotBlank
    public Date date;
	
	public String toString() {
	        return String.format(
	                "post[id=%s, userId=%s, contents='%s', date='%s']",
	                id, userId, contents, date);
	}
	public Post toEntity() {
		return new Post(ObjectId.get(), userId, contents, date);
	}
    
}
