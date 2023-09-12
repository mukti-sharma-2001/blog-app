package com.mukti.blog1.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mukti.blog1.config.AppConstants;
import com.mukti.blog1.payloads.ApiResponse;
import com.mukti.blog1.payloads.PostDto;
import com.mukti.blog1.payloads.PostResponse;
import com.mukti.blog1.services.FileService;
import com.mukti.blog1.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {
	@Autowired
	private PostService postService;
	@Autowired
	private FileService fileService;
	@Value("${project.image}")
	private String path;
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto>createPost(
			@RequestBody PostDto postDto,
			@PathVariable Integer userId,
			@PathVariable Integer categoryId 
			)
	{
		PostDto createdPost=postService.createPost(postDto,userId, categoryId);
		return new ResponseEntity<PostDto>(createdPost,HttpStatus.CREATED);
	}
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId){
		PostDto updatedPost=postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.CREATED);
	}
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
		List<PostDto>posts=postService.getPostsByUser(userId);
		return ResponseEntity.ok(posts);
	}
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
		List<PostDto>posts=postService.getPostsByCategory(categoryId);
		return ResponseEntity.ok(posts);
	}
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		PostDto postDto=postService.getPostById(postId);
		return ResponseEntity.ok(postDto);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER,required=false)Integer pageNumber ,
			@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false) Integer pageSize,
			@RequestParam (value="sortBy",defaultValue=AppConstants.SORT_BY,required=false)String sortBy,
			@RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR,required=false)String sortDir
			)
	{
		PostResponse postsList=postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
		return ResponseEntity.ok(postsList);
	}
	@GetMapping("/posts/search/title/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keyword")String keyword){
		List<PostDto>postDtos=postService.searchPostsByTitle(keyword);
		return ResponseEntity.ok(postDtos);
	}
	@GetMapping("/posts/search/content/{keyword}")
	public ResponseEntity<List<PostDto>> searchByContent(@PathVariable("keyword")String keyword){
		List<PostDto>postDtos=postService.searchByContent(keyword);
		return ResponseEntity.ok(postDtos);
	}
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
		postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("post deleted successfully", true),HttpStatus.OK);
	}
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,@PathVariable Integer postId) throws IOException{
		PostDto postDto=postService.getPostById(postId);
		System.out.println(postDto);
		String fileName=fileService.uploadImage(path,image);
		System.out.println(fileName);
		postDto.setImageName(fileName);
		PostDto updatePost=postService.updatePost(postDto, postId);
		System.out.println(updatePost);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);	
	}
	@GetMapping(value="/post/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable String imageName,HttpServletResponse response) throws IOException {
		InputStream resource=fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
