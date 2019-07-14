package com.wabu.d2project.user;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    public List<User> findByName(String user_name);
    public List<User> findByUserId(String user_id);
}