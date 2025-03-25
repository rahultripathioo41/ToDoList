package com.practice.entites;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class User {
	@NotNull
	@NotBlank(message="name can't be blank")
	@Size(min=3,max=15,message="username must be in between 3-15 characters")
	private String name;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotNull
	@Email(message="incorrect format")
	private String email;
	
	@Size(min=6,message="password must be atleast 6 characters")
	private String password;
	private String role;
	private String imageUrl;
	private Boolean status;
	
	//for getting contacts
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
	@JsonIgnore
	private List<Task> task=new ArrayList<Task>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public List<Task> getTask() {
		return task;
	}

	public void setTask(List<Task> task) {
		this.task = task;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", id=" + id + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", imageUrl=" + imageUrl + ", status=" + status +"]";
	}

	
	
	
	
	
}
