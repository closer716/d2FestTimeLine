package com.wabu.d2project;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wabu.d2project.post.Post;

@RestController
public class PostController {

	List<Post> posting = new ArrayList<>();

	@GetMapping("/getPosts")
	public ResponseEntity<Object> getPosts() {
		ServiceResponse<List<Post>> response = new ServiceResponse<>("success", posting);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
}