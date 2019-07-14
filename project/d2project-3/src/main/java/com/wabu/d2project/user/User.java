package com.wabu.d2project.user;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="user")
public class User {

    @Id
    public ObjectId _id;

    public String address;
    public String name;

    public User() {}

    public User(String name, String address) {
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return String.format(
                "user[id=%s, name='%s', address='%s']",
                _id, name, address);
    }

}