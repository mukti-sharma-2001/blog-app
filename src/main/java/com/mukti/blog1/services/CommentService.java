package com.mukti.blog1.services;

import com.mukti.blog1.payloads.CommentDto;

public interface CommentService {
	CommentDto createComment(CommentDto commentDto,Integer postId);
	void deleteComment(Integer commentId);
}
