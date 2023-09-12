package com.mukti.blog1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mukti.blog1.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
