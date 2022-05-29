package com.news.api.models.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
	@JsonBackReference
	private List<News> savedNews = new ArrayList<>();

	@DocumentReference(lazy = true, collection = "news")
	@JsonBackReference(value = "1")
	private List<News> posted = new ArrayList<>();

	@DocumentReference(lazy = true, collection = "company")
	@JsonBackReference(value = "2")
	private List<Company> currentJobs = new ArrayList<>();

	@DocumentReference(lazy = true, collection = "company")
	@JsonBackReference(value = "3")
	private List<Company> hasWorked = new ArrayList<>();

	@DocumentReference(lazy = true, collection = "company")
	@JsonBackReference(value = "4")
	private List<Company> following = new ArrayList<>();

	@DocumentReference(lazy = true, collection = "company")
	@JsonBackReference(value = "5")
	private List<Comment> comments = new ArrayList<>();

	@DocumentReference(lazy = true, collection = "news")
	@JsonBackReference(value = "6")
	private List<News> likedNews = new ArrayList<>();

	@DocumentReference(lazy = true, collection = "comment")
	@JsonBackReference(value = "7")
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

	public List<Company> getCurrentJobs() {
		return currentJobs;
	}

	public User addCurrentJobs(Company company){
		if(!currentJobs.contains(company)){
			currentJobs.add(company);
		}
		return this;
	}

	public User removeCurrentJobs(Company company){
		if(currentJobs.contains(company)){
			currentJobs.remove(company);
		}
		return this;
	}

	public List<Company> getHasWorked() {
		return hasWorked;
	}

	public User addHasWorked(Company company){
		if(!hasWorked.contains(company)){
			hasWorked.add(company);
		}
		return this;
	}

	public User removeHasWorked(Company company){
		if(hasWorked.contains(company)){
			hasWorked.remove(company);
		}
		return this;
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

	public User putLikeComment(Comment comment){
		if(likedComments.contains(comment)){
			likedComments.remove(comment);
		}else{
			likedComments.add(comment);
		}
		return this;
	}

	public User putLikeNews(News news){
		if(likedNews.contains(news)){
			likedNews.remove(news);
		}else{
			likedNews.add(news);
		}
		return this;
	}


	public User addPosted(News news){
		if(!posted.contains(news)){
			posted.add(news);
		}
		return this;
	}

	public User addComment(Comment comment){
		if(!comments.contains(comment)){
			comments.add(comment);
		}
		return this;
	}

	public User addSavedNews(News news){
		if(!savedNews.contains(news)){
			savedNews.add(news);
		}
		return this;
	}

	public User removeSavedNews(News news){
		if(savedNews.contains(news)){
			savedNews.remove(news);
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
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
}
