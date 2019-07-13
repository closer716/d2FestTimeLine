package com.wabu.d2project;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="customer")
public class Customer {

    @Id
    public ObjectId _id;

    public String address;
    public String name;

    public Customer() {}

    public Customer(String name, String address) {
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%s, name='%s', address='%s']",
                _id, name, address);
    }

}