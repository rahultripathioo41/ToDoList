package com.practice.controller;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.practice.entites.Task;
import com.practice.entites.User;
import com.practice.service.TaskService;
import com.practice.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/user")
public class Usercontroller {
	
	
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private TaskService taskService;
	
	private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/profiles/";

	
	
	
	@GetMapping("user_dashboard")
	public String index(Model model) {
	    
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user=userservice.getUserDao().findByEmail(auth.getName());
	    
	    System.out.println("Logged in as: " + auth.getName());
	    System.out.println("Role: " + auth.getAuthorities());
	    
		/*
		 * for ( Task t:taskService.getTaskDao().findByUser(user.getId())) {
		 * 
		 * System.out.println(t); }
		 */
	    model.addAttribute("tasks", taskService.getTaskDao().findByUser(user.getId()));
	    model.addAttribute("user", user);
	    

	    return "user/user_dashboard";
	}
	
	@GetMapping("profile")
	public String updateProfilePage(Model model)
	{
		
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user=userservice.getUserDao().findByEmail(auth.getName());
		model.addAttribute("user", user);
		return "user/updateProfile";
	}
	
	@PostMapping("updateProfile")
	public String updateProfile(@RequestParam("name") String name, @RequestParam("image") MultipartFile file)
	
	{
		if (!file.isEmpty()) {
	        try {
	            // Create directory if not exists
	            File directory = new File(UPLOAD_DIR);
	            if (!directory.exists()) {
	                directory.mkdirs(); // ✅ Create directory if it doesn't exist
	            }

	            // Save the file
	            String fileName = file.getOriginalFilename();
	            String filePath = directory.getAbsolutePath() + File.separator + fileName;
	            file.transferTo(new File(filePath)); // ✅ Save the file
                //retrieving user from authentication object
                org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                User user=userservice.getUserDao().findByEmail(auth.getName());
                user.setImageUrl("/profiles/" + fileName);
                user.setName(name);
                userservice.getUserDao().save(user);
                
                System.out.println("File uploaded to: " + filePath);
                
        		
                return "redirect:user_dashboard";
                
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:user_dashboard";
            }
        } 
		
		return "redirect:user_dashboard";
	}
	
	
	
	@PostMapping("deleteTask")
    @ResponseBody
    public String deleteTask(@RequestParam("taskId") int taskId) {
        try {
            taskService.getTaskDao().deleteById(taskId);
            return "Task deleted successfully";
        } catch (Exception e) {
            return "Failed to delete task";
        }
    }

	@GetMapping("addtask")
	public String addtask(Model model) {
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user=userservice.getUserDao().findByEmail(auth.getName());
		model.addAttribute("task", new Task());
		model.addAttribute("user",user);
		return "user/user_addTask";
	}
	
	@PostMapping("saveTask")
	public String saveTask(@Valid @ModelAttribute Task task,BindingResult result)
	{
		if (result.hasErrors()) {
            return "user/user_addTask";
        }
		
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user=userservice.getUserDao().findByEmail(auth.getName());
        // Set user manually if required
		task.setUser(user);

        taskService.getTaskDao().save(task);
		
		return  "redirect:user_dashboard";
	}
	
	@GetMapping("clearTasks")
	public String clearTask(Model model)
	{
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user=userservice.getUserDao().findByEmail(auth.getName());
		taskService.getTaskDao().deleteById(user.getId());
		model.addAttribute("user", user);
		
		return "user/user_dashboard";
	}
	
	@GetMapping("{userId}")
	@ResponseBody
	public Object getUser(@PathVariable int userId,Model model)
	{
		model.addAttribute("user2",userservice.getUserDao().findById(userId));
		System.out.println(userservice.getUserDao().findById(userId));
		return userservice.getUserDao().findById(userId);
	}
	
	@PostMapping("updateRole")
	@ResponseBody
	public String updateRole(@RequestParam("userId") int userId, @RequestParam("role") String role) {
	    Optional<User> userOpt = userservice.getUserDao().findById(userId);
	    if (userOpt.isPresent()) {
	        User user = userOpt.get();
	        user.setRole(role);
	        userservice.getUserDao().save(user);
	        return "User role updated successfully";
	    }
	    return "Failed to update user role";
	}
	
	@PostMapping("updateStatus")
	@ResponseBody
	public String updateStatus(@RequestParam("userId") int userId, @RequestParam("status") boolean status) {
	    Optional<User> userOpt = userservice.getUserDao().findById(userId);
	    if (userOpt.isPresent()) {
	        User user = userOpt.get();
	        user.setStatus(status);
	        userservice.getUserDao().save(user);
	        return "User status updated successfully";
	    }
	    return "Failed to update user status";
	}



}
