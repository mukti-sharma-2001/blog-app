package com.mukti.blog1.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mukti.blog1.entities.Category;
import com.mukti.blog1.entities.Post;
import com.mukti.blog1.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category); 
	List<Post> findByTitleContaining(String title);
	@Query("Select p from Post p where p.content like:key")
	List<Post> searchByContent(@Param("key")String content);
}
