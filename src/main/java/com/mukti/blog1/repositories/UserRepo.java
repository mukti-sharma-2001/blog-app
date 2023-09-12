package com.mukti.blog1.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mukti.blog1.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String username);
	
	
}
