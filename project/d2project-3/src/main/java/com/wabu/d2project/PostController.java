package com.wabu.d2project;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wabu.d2project.post.Post;
import com.wabu.d2project.post.PostDto;
import com.wabu.d2project.post.PostService;
import com.wabu.d2project.user.User;
import com.wabu.d2project.user.UserService;

@RestController
public class PostController {

	List<Post> posting = new ArrayList<>();
	private int from=0;
	private static final int pageNum=10;
	
	@Autowired
	PostService postService;
	@Autowired
	UserService userService;

	@GetMapping("/getPosts")
	public ResponseEntity<Object> getPosts() throws Exception{
		User user=userService.getUserById("3ny7nktgjibq3");
		from+=pageNum;
		ArrayList<String> postId = userService.getPostId(user.getId(), user.getRegistrationDate(), from, pageNum);
		ServiceResponse<List<PostDto>> response = new ServiceResponse<>("success", postService.findBy_id(postId));
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
}