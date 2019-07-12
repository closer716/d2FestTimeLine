package com.wabu.d2project;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Customer {

    @Id
    public String id;

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
                id, name, address);
    }

}