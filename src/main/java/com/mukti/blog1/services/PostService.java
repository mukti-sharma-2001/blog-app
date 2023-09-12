package com.mukti.blog1.services;

import java.util.List;

import com.mukti.blog1.payloads.PostDto;
import com.mukti.blog1.payloads.PostResponse;

public interface PostService {
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	PostDto updatePost(PostDto postDto,Integer postId);
	void deletePost(Integer postId);
	PostResponse getAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	PostDto getPostById(Integer postId);
	List<PostDto>getPostsByCategory(Integer CategoryId);
	List<PostDto> getPostsByUser(Integer userId);
	List<PostDto> searchPostsByTitle(String keyword);
	List<PostDto>searchByContent(String keyword);
}
