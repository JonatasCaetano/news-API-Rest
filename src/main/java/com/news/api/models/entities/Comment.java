package com.news.api.models.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.news.api.models.entities.dtos.CommentDto;


@Document
public class Comment {

	@Id
	private String id;
	private String body;
	private LocalDateTime creationDate;
	@DocumentReference(lazy = true, collection = "user")
	private User author;
	@DocumentReference(lazy = true, collection = "user")
	private List<User> likes = new ArrayList<>();
	
	public Comment() {
		super();
	}

	public Comment(String id, String body, LocalDateTime creationDate) {
		super();
		this.id = id;
		this.body = body;
		this.creationDate = creationDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public List<User> getLikes() {
		return likes;
	}
	
	public CommentDto toCommentDto() {
		return new CommentDto(this);
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
		Comment other = (Comment) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
	
	
}
