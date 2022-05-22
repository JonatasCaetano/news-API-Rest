package com.news.api.models.entities.dtos;

import java.time.LocalDateTime;

import com.news.api.models.entities.Company;
import com.news.api.models.entities.News;
import com.news.api.models.entities.User;

public class NewsDto {

	private String id;
	private String title;
	private String body;
	private LocalDateTime creationDate;
	private String image;
	private UserDto author;
	private CompanyDto publisher;
	private int quantityLikes;
	private int quantityComments;
	private int quantityViews;
	
	public NewsDto() {
		super();
	}
	
	public NewsDto(News news) {
		super();
		id = news.getId();
		title = news.getTitle();
		body = news.getBody();
		creationDate = news.getCreationDate();
		image = news.getImage();
		setAuthor(news.getAuthor());
		setPublisher(news.getPublisher());
		quantityLikes = news.getLikes().size();
		quantityComments = news.getComments().size();
		quantityViews = news.getUsersViews().size();
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

	public UserDto getAuthor() {
		return author;
	}

	public void setAuthor(User user) {
		this.author = new UserDto(user);
	}

	public CompanyDto getPublisher() {
		return publisher;
	}

	public void setPublisher(Company company) {
		this.publisher = new CompanyDto(company);
	}

	public int getQuantityLikes() {
		return quantityLikes;
	}

	public void setQuantityLikes(int quantityLikes) {
		this.quantityLikes = quantityLikes;
	}

	public int getQuantityComments() {
		return quantityComments;
	}

	public void setQuantityComments(int quantityComments) {
		this.quantityComments = quantityComments;
	}

	public int getQuantityViews() {
		return quantityViews;
	}

	public void setQuantityViews(int quantityViews) {
		this.quantityViews = quantityViews;
	}
	
	
	
	
}
