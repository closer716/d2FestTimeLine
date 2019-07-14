package com.wabu.d2project.user;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    public List<User> findByName(String name);
    public List<User> findByAddress(String address);
}