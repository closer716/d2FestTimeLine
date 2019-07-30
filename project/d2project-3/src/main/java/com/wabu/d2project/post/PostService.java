package com.wabu.d2project.post;

import java.util.Date;
import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

@EnableMongoRepositories(basePackages = "com.wabu.d2project.post")
@Service
public class PostService {
	
	@Autowired
	private PostRepository repository;
	
	public ArrayList<Post> findByUserId(String userId) {
		return repository.findByUserId(userId);
	}
	
	public Post addPost(PostDto PostDto) {
		return repository.save(PostDto.toEntity());
	}
	
	public ArrayList<PostDto> findByUserIdAndDateBetween(ArrayList<String> userId, Date to, Date from){
		return findByUserIdAndDateBetween(userId, to, from);
	}
	
	public ArrayList<PostDto> findBy_id(ArrayList<String> _id) {
		return repository.findBy_id(_id);
	}
	
	public void deleteByUserId(String userId) {
		repository.deleteByUserId(userId);
	}
	
	public void deleteAll() {
		repository.deleteAll();
	}
}
