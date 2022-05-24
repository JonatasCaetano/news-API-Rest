package com.news.api.models.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.news.api.models.entities.dtos.NewsDto;

@Document
public class News {

	@Id
	private String id;
	private String title;
	private String body;
	private LocalDateTime creationDate;
	private String image;
	private Boolean visible;
	@DocumentReference(lazy = true, collection = "user")
	private User author;
	@DocumentReference(lazy = true, collection = "company")
	private Company publisher;
	@DocumentReference(lazy = true, collection = "user")
	private List<User> likes = new ArrayList<>();
	@DocumentReference(lazy = true, collection = "comment")
	private List<Comment> comments = new ArrayList<>();
	@DocumentReference(lazy = true, collection = "user")
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
	
	public NewsDto toNewsDto() {
		return new NewsDto(this);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		News other = (News) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
	
}
