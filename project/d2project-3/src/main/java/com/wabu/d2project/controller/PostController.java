package com.wabu.d2project.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wabu.d2project.ServiceResponse;
import com.wabu.d2project.post.Post;
import com.wabu.d2project.post.PostDto;
import com.wabu.d2project.post.PostService;
import com.wabu.d2project.user.User;
import com.wabu.d2project.user.UserService;
import com.wabu.d2project.user.dataContainer.Friend;
import com.wabu.d2project.user.dataContainer.Notification;

@RestController
public class PostController {

	List<Post> posting = new ArrayList<>();
	public static final int pageNum=10;
	
	@Autowired
	PostService postService;
	@Autowired
	UserService userService;

	@GetMapping("/getPosts")
	public ResponseEntity<Object> getPosts(@AuthenticationPrincipal User user, Model model, @RequestParam(value="from") int from) throws Exception{
		ArrayList<String> postId = userService.getPostId(user.getId(), user.getRegistrationDate(), from, pageNum);
		ServiceResponse<List<PostDto>> response = new ServiceResponse<>("success", postService.findBy_id(postId));
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
}