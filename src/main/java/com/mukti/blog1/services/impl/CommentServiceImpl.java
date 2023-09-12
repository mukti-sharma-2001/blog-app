package com.mukti.blog1.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mukti.blog1.entities.Comment;
import com.mukti.blog1.entities.Post;
import com.mukti.blog1.exception.ResourceNotFoundException;
import com.mukti.blog1.payloads.CommentDto;
import com.mukti.blog1.repositories.CommentRepo;
import com.mukti.blog1.repositories.PostRepo;
import com.mukti.blog1.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post Id", postId));
		Comment comment=modelMapper.map(commentDto,Comment.class);
		comment.setPost(post);
		Comment savedComment=this.commentRepo.save(comment); 
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		// TODO Auto-generated method stub
		Comment comment=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Comment Id",commentId));
		commentRepo.delete(comment);

	}

}
