package com.mukti.blog1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mukti.blog1.payloads.ApiResponse;
import com.mukti.blog1.payloads.CommentDto;
import com.mukti.blog1.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {
	@Autowired
	private CommentService commentService;
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComments(@RequestBody CommentDto commentDto,@PathVariable Integer postId ){
		CommentDto createdComment=commentService.createComment(commentDto, postId);
		return new ResponseEntity<CommentDto>(createdComment,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfully!!",true),HttpStatus.OK);
	}

}
