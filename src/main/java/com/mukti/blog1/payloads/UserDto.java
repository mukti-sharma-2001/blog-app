package com.mukti.blog1.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

public class UserDto {
	private Integer id;
	@NotEmpty
	@Email
	private String email;
	@NotEmpty
	@Size(min=3,message="Name should atleast have 3 characters")
	private String name;
	@NotEmpty
	@Size(min=5, max=10,message="Password should contain atleast 5 characters and atmost 10 characters")
	private String password;
	@NotEmpty
	@Size(min=5)
	private String about;
	private Set<RoleDto> roles=new HashSet<>(); 
	
	public Set<RoleDto> getRoles() {
		return roles;
	}
	public void setRoles(Set<RoleDto> roles) {
		this.roles = roles;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	
}
