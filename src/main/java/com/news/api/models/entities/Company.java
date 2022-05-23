package com.news.api.models.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document
public class Company {

	@Id
	private String id;
	private String name;
	private String email;
	private String password;
	private LocalDateTime creationDate;
	private String image;
	@DocumentReference(lazy = true, collection = "news")
	private List<News> posted = new ArrayList<>();
	@DocumentReference(lazy = true, collection = "user")
	private List<User> currentWriters = new ArrayList<>();
	@DocumentReference(lazy = true, collection = "user")
	private List<User> formerWriters = new ArrayList<>();
	@DocumentReference(lazy = true, collection = "user")
	private List<User> followers = new ArrayList<>();
	
	public Company() {
		super();
	}

	public Company(String id, String name, String email, String password, LocalDateTime creationDate, String image) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.creationDate = creationDate;
		this.image = image;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public List<News> getPosted() {
		return posted;
	}

	public List<User> getCurrentWriters() {
		return currentWriters;
	}

	public List<User> getFormerWriters() {
		return formerWriters;
	}

	public List<User> getFollowers() {
		return followers;
	}
	
	
	
}
