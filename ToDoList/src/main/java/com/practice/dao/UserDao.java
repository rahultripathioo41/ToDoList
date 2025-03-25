package com.practice.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.practice.entites.User;

@Component
public interface UserDao extends CrudRepository<User,Integer>{
	User findByEmail(String email);
	
}
