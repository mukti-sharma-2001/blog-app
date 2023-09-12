package com.mukti.blog1.services.impl;

import java.util.List;
import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mukti.blog1.config.AppConstants;
import com.mukti.blog1.entities.Role;
import com.mukti.blog1.entities.User;
import com.mukti.blog1.exception.ResourceNotFoundException;
import com.mukti.blog1.payloads.UserDto;
import com.mukti.blog1.repositories.RoleRepo;
import com.mukti.blog1.repositories.UserRepo;
import com.mukti.blog1.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepo roleRepo;
	@Override
	public UserDto createUser(UserDto userDto) {
		User user=dtoToUser(userDto);
		User savedUSer=userRepo.save(user);
		return userToDto(savedUSer);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user=userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updatedUser=userRepo.save(user);
		return userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id" , userId));
		return userToDto(user);
		
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users=userRepo.findAll();
		List<UserDto> userDtos=users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id" , userId));
		userRepo.delete(user);

	}
	public User dtoToUser(UserDto userDto) {
		User user=modelMapper.map(userDto, User.class);
//		user.setId(userDto.getId());
//		user.setEmail(userDto.getEmail());
//		user.setName(userDto.getName());
//		user.setPassword(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
		return user;
	}
	public UserDto userToDto(User user) {
		UserDto userDto=modelMapper.map(user,UserDto.class);
		return userDto;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user=this.modelMapper.map(userDto, User.class);
		//encode the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		//roles
		Role role=this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		User newUser=this.userRepo.save(user);
		return this.modelMapper.map(newUser,UserDto.class);
	}

}
