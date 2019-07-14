package com.wabu.d2project.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

//@EnableMongoRepositories(basePackages = "com.wabu.d2project.user")
@Service
public class PostService {
	
	@Autowired
	private PostRepository repository;
	
	public void printDB() {
		System.out.println(repository.findAll().toString());
	}
	
	public Post addPost(PostDto userDto) {
		return repository.save(userDto.toEntity());
	}
}
