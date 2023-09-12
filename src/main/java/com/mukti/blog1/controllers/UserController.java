package com.mukti.blog1.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mukti.blog1.payloads.ApiResponse;
import com.mukti.blog1.payloads.UserDto;
import com.mukti.blog1.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;

	//Post-create user
	@PostMapping("/")
	ResponseEntity<UserDto>createUser(@Valid @RequestBody UserDto userDto){ 
		UserDto createUserDto=userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
	}
	//Put - Update User
	@PutMapping("/{userId}")
	ResponseEntity<UserDto>updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uid){
		UserDto userDto1=userService.updateUser(userDto, uid);
		return ResponseEntity.ok(userDto1);
	}
	//Delete- delete user
	@DeleteMapping("/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId")int uid){
		userService.deleteUser(uid);
		return new ResponseEntity<>(new ApiResponse("User deleted successfully",true),HttpStatus.OK);
		
	}
	//Get- get all user and get 1 user
	@GetMapping("/")
	ResponseEntity<List<UserDto>> getAllUsers(){
		return new ResponseEntity<List<UserDto>>(userService.getAllUsers(),HttpStatus.OK);
	}
	@GetMapping("/{userId}")
	ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId){
		return new ResponseEntity<UserDto>(userService.getUserById(userId),HttpStatus.OK);
	}
	
}
