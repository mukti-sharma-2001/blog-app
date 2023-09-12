package com.mukti.blog1.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;



import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mukti.blog1.entities.Category;
import com.mukti.blog1.entities.Post;
import com.mukti.blog1.entities.User;
import com.mukti.blog1.exception.ResourceNotFoundException;
import com.mukti.blog1.payloads.PostDto;
import com.mukti.blog1.payloads.PostResponse;
import com.mukti.blog1.repositories.CategoryRepo;
import com.mukti.blog1.repositories.PostRepo;
import com.mukti.blog1.repositories.UserRepo;
import com.mukti.blog1.services.PostService;


@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private PostResponse postResponse;
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		User user =userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "user id", userId));
		Category category=categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "category id", categoryId));
//		System.out.println(user);
//		System.out.println(category);
		Post post = modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);		
		Post createdPost=postRepo.save(post);
		return modelMapper.map(createdPost,PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post=postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post Id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(post.getContent());
		post.setAddedDate(new Date());
		post.setImageName(postDto.getImageName());
		Post updatedPost=postRepo.save(post);
		return modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post=postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));
		postRepo.delete(post);

	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		Sort sort=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable p=PageRequest.of(pageNumber, pageSize, sort);
		Page<Post>pagePost=postRepo.findAll(p);
		List<Post>allPosts=pagePost.getContent();
		List<PostDto>postDtos=allPosts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pageNumber);
		postResponse.setPageSize(pageSize);
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post=postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		System.out.println(categoryId);
		Category cat=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","category id",categoryId));
		List<Post>posts=postRepo.findByCategory(cat);
		System.out.println(posts);
		List<PostDto>postDtos=posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
		
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		
		System.out.println(userId);
		User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "user id", userId));
		List<Post> posts=postRepo.findByUser(user);
		System.out.println(posts);
		List<PostDto>postDtos=posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPostsByTitle(String keyword) {
		List<Post> posts=postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos=posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}
	@Override
	public List<PostDto> searchByContent(String keyword) {
		List<Post> posts=postRepo.searchByContent("%"+keyword+"%");
		List<PostDto> postDtos=posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
		
	}
	

}
