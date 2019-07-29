package com.wabu.d2project.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wabu.d2project.ServiceResponse;
import com.wabu.d2project.post.Post;
import com.wabu.d2project.post.PostDto;
import com.wabu.d2project.post.PostService;
import com.wabu.d2project.user.User;
import com.wabu.d2project.user.UserService;
import com.wabu.d2project.util.Constant;
import com.wabu.d2project.util.Util;

@RestController
public class PostController {

   List<Post> posting = new ArrayList<>();

   @Autowired
   PostService postService;
   @Autowired
   UserService userService;
   private Util util = new Util();

   @GetMapping("/getPosts")
   public ResponseEntity<Object> getPosts(@AuthenticationPrincipal User user, Model model, @RequestParam(value="from") int from) throws Exception{
      ArrayList<String> postId = userService.getPostId(user.getId(), user.getRegistrationDate(), from, Constant.pageNum);
      ServiceResponse<List<PostDto>> response = new ServiceResponse<>("success", postService.findBy_id(postId));
      return new ResponseEntity<Object>(response, HttpStatus.OK);
   }
   
   
   @PostMapping("/savePost")
   public ResponseEntity<Object> savePost(@AuthenticationPrincipal User user, Model model, @RequestBody String contents) throws Exception {
      Date date = new Date();
      SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String a = formattedDate.format(date);
      PostDto post = new PostDto(ObjectId.get(), user.getId(), user.getName(), contents,formattedDate.parse(a));
      postService.addPost(post);
      userService.addPost(post);
      ServiceResponse<PostDto> response = new ServiceResponse<>("success", post);
      return new ResponseEntity<Object>(response, HttpStatus.OK);
   }
   
   /* 포스트 등록 */
	@RequestMapping(value="/addPost", method=RequestMethod.GET)
	public String addPost(@AuthenticationPrincipal User user, Model model, @RequestParam("contents") String contents) throws Exception{
		util.addPost(user.getId(),user.getName(),contents, userService, postService);
		return "contents/timeline";
	}
}