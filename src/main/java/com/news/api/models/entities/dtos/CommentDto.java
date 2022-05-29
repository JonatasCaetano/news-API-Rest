package com.news.api.models.entities.dtos;

import java.time.LocalDateTime;

import com.news.api.models.entities.Comment;
import com.news.api.models.entities.User;

public class CommentDto {

	private String id;
	private String body;
	private LocalDateTime creationDate;
	private UserDto author;
	private int quantityLikes;
	private Boolean liked = false;

	public CommentDto() {
		super();
	}
	
	public CommentDto(Comment comment) {
		super();
		id = comment.getId();
		body = comment.getBody();
		creationDate = comment.getCreationDate();
		setAuthor(comment.getAuthor());
		quantityLikes = comment.getLikes().size();
	}

	public CommentDto(Comment comment, User user) {
		super();
		id = comment.getId();
		body = comment.getBody();
		creationDate = comment.getCreationDate();
		setAuthor(comment.getAuthor());
		quantityLikes = comment.getLikes().size();
		setLiked(comment, user);
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

	public UserDto getAuthor() {
		return author;
	}

	public void setAuthor(User user) {
		this.author = new UserDto(user);
	}

	public int getQuantityLikes() {
		return quantityLikes;
	}

	public void setQuantityLikes(int quantityLikes) {
		this.quantityLikes = quantityLikes;
	}

	public Boolean getLiked() {
		return this.liked;
	}
	
	public void setLiked(Comment comment, User user){
		if(comment.getLikes().contains(user)){
			liked = true;
		}else{
			liked = false;
		}
	}
	
	
	
}
