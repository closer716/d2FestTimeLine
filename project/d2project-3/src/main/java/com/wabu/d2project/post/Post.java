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

@AllArgsConstructor
@NoArgsConstructor
@Document(collection="post")
public class Post {

    @Id
    public ObjectId id;

    public String userId;
    public String contents;
    public Date date;

    @Override
    public String toString() {
    	
        return String.format(
                "post[id=%s, userId=%s, contents='%s', date='%s']",
                id, userId, contents, date);
    }

}