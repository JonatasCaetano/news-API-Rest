package com.news.api.models.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.news.api.models.entities.dtos.NewsDto;

@Document
public class News {

	@Id
	private String id;
	private String title;
	private String body;
	private LocalDateTime creationDate;
	private String image;
	private Boolean visible = true;

	@DocumentReference(lazy = true, collection = "user")
	@JsonBackReference(value = "14")
	private User author;

	@DocumentReference(lazy = true, collection = "company")
	@JsonBackReference(value = "15")
	private Company publisher;

	@DocumentReference(lazy = true, collection = "user")
	@JsonBackReference(value = "16")
	private List<User> likes = new ArrayList<>();

	@DocumentReference(lazy = true, collection = "comment")
	private List<Comment> comments = new ArrayList<>();

	@DocumentReference(lazy = true, collection = "user")
	@JsonBackReference(value = "17")
	private List<User> usersViews = new ArrayList<>();
	
	public News() {
		super();
	}

	public News(String title, String body, String image) {
		super();
		this.title = title;
		this.body = body;
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

	public void setPublisher(Company publisher) {
		this.publisher = publisher;
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

	public NewsDto toNewsDto(User user) {
		return new NewsDto(this, user);
	}

	public News putLike(User user){
		if(likes.contains(user)){
			likes.remove(user);
		}else{
			likes.add(user);
		}
		return this;
	}

	public News addComment(Comment comment){
		if(!comments.contains(comment)){
			comments.add(comment);
		}
		return this;
	}

	public News removeComment(Comment comment){
		if(comments.contains(comment)){
			comments.remove(comment);
		}
		return this;
	}

	public News putVisibility(){
		visible = !visible;
		return this;
	}

	public News addUserView(User user){
		if(!usersViews.contains(user)){
			usersViews.add(user);
		}
		return this;
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
