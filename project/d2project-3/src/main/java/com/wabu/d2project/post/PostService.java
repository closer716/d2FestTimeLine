package com.wabu.d2project.post;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

@EnableMongoRepositories(basePackages = "com.wabu.d2project.post")
@Service
public class PostService {
	
	@Autowired
	private PostRepository repository;
	
	public void printDB() {
		System.out.println(repository.findAll().toString());
	}
	
	public Post addPost(PostDto PostDto) {
		return repository.save(PostDto.toEntity());
	}
	
	public List<Post> findByUserIdAndDateBetween(List<String> userId, Date to, Date from){
		return findByUserIdAndDateBetween(userId, to, from);
	}
}
