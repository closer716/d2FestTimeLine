package com.wabu.d2project.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@EnableMongoRepositories(basePackages = "com.wabu.d2project.user")
@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	public void printDB() {
		System.out.println(repository.findAll().toString());
	}
	
	public User addUser(UserDto userDto) {
		return repository.save(userDto.toEntity());
	}
}
