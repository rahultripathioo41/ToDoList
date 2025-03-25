package com.practice.service;

//import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.practice.dao.UserDao;
import com.practice.entites.User;


@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public UserDao getUserDao() {
		return userDao;
	}


	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}


	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}


	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}


	public void registerUser(User user) throws Exception 
	{
		//user=userDao.findByEmail(user.getEmail());
		
		if(userDao.findByEmail(user.getEmail())==null)
		{
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			///user.setStatus(true);
			user.setRole("ROLE_USER");
	        user.setStatus(true);
	        user.setImageUrl("/profiles/default.png");
	        
			
			userDao.save(user);
		}
		else {
			System.out.println("registration failed");
			throw new NoSuchMethodException("User already exist");
		}
	}
	
	
	
}
