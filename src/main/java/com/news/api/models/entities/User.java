package com.news.api.models.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document
public class User {

	@Id
	private String id;
	private String name;
	private String email;
	private String password;
	private LocalDateTime creationDate;
	private String image;
	@DocumentReference(lazy = true, collection = "News")
	private List<News> savedNews = new ArrayList<>();
	@DocumentReference(lazy = true, collection = "News")
	private List<News> posted = new ArrayList<>();
	@DocumentReference(lazy = true, collection = "Company")
	private List<Company> currentJob = new ArrayList<>();
	@DocumentReference(lazy = true, collection = "Company")
	private List<Company> hasWorked = new ArrayList<>();
	@DocumentReference(lazy = true, collection = "Company")
	private List<Company> following = new ArrayList<>();
	@DocumentReference(lazy = true, collection = "Company")
	private List<Comment> comments = new ArrayList<>();
			
	public User() {
		super();
	}

	public User(String id, String name, String email, String password, LocalDateTime creationDate, String image) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.creationDate = creationDate;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<News> getSavedNews() {
		return savedNews;
	}

	public List<News> getPosted() {
		return posted;
	}

	public List<Company> getCurrentJob() {
		return currentJob;
	}

	public List<Company> getHasWorked() {
		return hasWorked;
	}

	public List<Company> getFollowing() {
		return following;
	}

	public List<Comment> getComments() {
		return comments;
	}
	
}
