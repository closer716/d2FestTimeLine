package com.wabu.d2project.post;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bson.BsonTimestamp;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="post")
public class Post {

    @Id
    public ObjectId _id;

    public String userId;
    public String contents;
    public Date date;

    public Post(String userId, String contents, Date date) {
        this.userId = userId;
        this.contents = contents;
        this.date = date;
    }

    @Override
    public String toString() {
    	
        return String.format(
                "post[id=%s, userId=%s, contents='%s', date='%s']",
                _id, userId, contents, date);
    }

}