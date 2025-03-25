package com.practice.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.practice.dao.UserDao;
import com.practice.entites.Message;
import com.practice.entites.User;
import com.practice.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String homePage()
	{
		return "home";
	}
	 
	@GetMapping("/signUp")
	public String signUp(Model model)
	{
		model.addAttribute("user", new User());
		model.addAttribute("message",null);
		return "signUp";
	}
	
	@GetMapping("/goTologInPage")
	public String showLoginPage(HttpServletRequest request, Model model) {
	    // Check for logout
	    if (request.getParameter("logout") != null) {
	        model.addAttribute("message", new Message("You have been logged out successfully.", "success"));
	    }
	    
	    // Check for authentication error message
	    HttpSession session = request.getSession(false);
	    if (session != null) {
	        Message message = (Message) session.getAttribute("message");
	        if (message != null) {
	            model.addAttribute("message", message);
	            session.removeAttribute("message"); // Clear after use
	        }
	    }
	    
	    return "logIn";
	}

	
//	@PostMapping("/login")   spring security automatically handles this endpoint
//	public String logIn()
//	{
//		return "user_dashboard";
//	}
	
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute User user, BindingResult result,
	                       @RequestParam("confirmPassword") String confirmPassword, Model model) {
	    
	    if (result.hasErrors()) {
	        model.addAttribute("user", user);
	        return "signUp";  // No message added, so the alert box won't appear
	    }

	    if (!user.getPassword().equals(confirmPassword)) {
	        model.addAttribute("message", new Message("Passwords do not match", "alert"));
	        return "signUp";
	    }

	    try {
	        
	        userService.registerUser(user);
	        model.addAttribute("message", new Message("Successfully registered", "success"));
	    }catch(NoSuchMethodException e){
	    	model.addAttribute("message",new Message("email already exists", "alert"));
	    	return "signUp";
	    }
	    catch(Exception e)
	    {
	    	model.addAttribute("message", new Message("something went wrong","alert"));
	    	return "signUp";
	    }
	    

	    return "logIn";
	}

}
