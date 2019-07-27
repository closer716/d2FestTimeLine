package com.wabu.d2project.post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	
	public List<Post> findByUserId(String userId) {
		return repository.findByUserId(userId);
	}
	
	
	public List<PostDto> findLast2ByUserId(String userId) {
		return repository.findLast2ByUserId(userId);
	}
	
	public void printDB() {
		System.out.println(repository.findAll().toString());
	}
	
	public Post addPost(PostDto PostDto) {
		return repository.save(PostDto.toEntity());
	}
	
	public List<PostDto> findByUserIdAndDateBetween(List<String> userId, Date to, Date from){
		return findByUserIdAndDateBetween(userId, to, from);
	}
	
	public List<PostDto> findBy_id(ArrayList<String> _id) {
		return repository.findBy_id(_id);
	}
	
	public void deleteByUserId(String userId) {
		repository.deleteByUserId(userId);
	}
	
	public void deleteAll() {
		repository.deleteAll();
	}
	
	public List<Post> findAll(){
		return repository.findAll();
	}
}
