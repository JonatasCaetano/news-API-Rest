package com.news.api.models.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document
public class News {

	@Id
	private String id;
	private String title;
	private String body;
	private LocalDateTime creationDate;
	private String image;
	private Boolean visible;
	private Integer views;
	@DocumentReference(lazy = true, collection = "User")
	private User author;
	@DocumentReference(lazy = true, collection = "Company")
	private Company publisher;
	@DocumentReference(lazy = true, collection = "User")
	private List<User> likes = new ArrayList<>();
	@DocumentReference(lazy = true, collection = "Comment")
	private List<Comment> comments = new ArrayList<>();
	@DocumentReference(lazy = true, collection = "User")
	private List<User> usersViews = new ArrayList<>();
	
	public News() {
		super();
	}

	public News(String id, String title, String body, LocalDateTime creationDate, String image) {
		super();
		this.id = id;
		this.title = title;
		this.body = body;
		this.creationDate = creationDate;
		this.image = image;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
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

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Company getPublisher() {
		return publisher;
	}

	public List<User> getLikes() {
		return likes;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public List<User> getUsersViews() {
		return usersViews;
	}
	
	
	
	
}
