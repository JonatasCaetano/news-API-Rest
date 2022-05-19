package com.news.api.models.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Company {

	@Id
	private String id;
	private String name;
	private String email;
	private String password;
	private LocalDateTime creationDate;
	private String image;
	
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
	
	
	
}
