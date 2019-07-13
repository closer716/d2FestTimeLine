package com.wabu.d2project;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
    public List<Customer> findByName(String name);
    public List<Customer> findByAddress(String address);
}