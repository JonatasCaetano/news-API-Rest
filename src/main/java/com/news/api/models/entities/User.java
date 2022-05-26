package com.news.api.models.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.news.api.models.entities.dtos.UserDto;

@Document
public class User {

	@Id
	private String id;
	private String name;
	private String email;
	private String password;
	private LocalDateTime creationDate;
	private String image;
	private LocalDateTime lastLogin;
	@DocumentReference(lazy = true, collection = "news")
	private List<News> savedNews = new ArrayList<>();
	@DocumentReference(lazy = true, collection = "news")
	private List<News> posted = new ArrayList<>();
	@DocumentReference(lazy = true, collection = "company")
	private List<Company> currentJob = new ArrayList<>();
	@DocumentReference(lazy = true, collection = "company")
	private List<Company> hasWorked = new ArrayList<>();
	@DocumentReference(lazy = true, collection = "company")
	private List<Company> following = new ArrayList<>();
	@DocumentReference(lazy = true, collection = "company")
	private List<Comment> comments = new ArrayList<>();
	@DocumentReference(lazy = true, collection = "news")
	private List<News> likedNews = new ArrayList<>();
	@DocumentReference(lazy = true, collection = "comment")
	private List<Comment> likedComments = new ArrayList<>();
			
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

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public List<News> getLikedNews() {
		return likedNews;
	}

	public List<Comment> getLikedComments() {
		return likedComments;
	}

	public User addFollowing(Company company){
		if(!following.contains(company)){
			following.add(company);
		}
		return this;
	}

	public User removeFollowing(Company company){
		if(following.contains(company)){
			following.remove(company);
		}
		return this;
	}

	public boolean isPassword(String password){
		if(password.equals(this.password)){
			return true;
		}else{
			return false;
		}
	}
	
	public UserDto toUserDto() {
		return new UserDto(this);
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
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
}
