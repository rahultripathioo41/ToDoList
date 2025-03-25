package com.practice.service;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.practice.dao.UserDao;
import com.practice.entites.CustomUserDetails;
import com.practice.entites.User;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	private   UserDao userDao;
	
	public CustomUserDetailsService(UserDao userDao)
	{
		this.userDao=userDao;
	}
	
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	    User user = userDao.findByEmail(email);

	    if (user == null) {
	        throw new UsernameNotFoundException("Invalid email or password!");
	    }

	    System.out.println("User found: " + user.getEmail() + ", Status: " + user.getStatus());

	    if (!user.getStatus()) {
	        throw new DisabledException("Your account is inactive. Please contact the admin.");
	    }

	    return new CustomUserDetails(user);
	}

}
