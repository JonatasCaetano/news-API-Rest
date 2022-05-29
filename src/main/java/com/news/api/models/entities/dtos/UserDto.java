package com.news.api.models.entities.dtos;

import com.news.api.models.entities.User;

public class UserDto {
    
    private String id;
	private String name;
    private String image;
	private int quantityLikesNews;
	private int quantityLikesComments;
	private int quantityComments;
	private int quantitySaved;
	private int quantityPosted;
	private int quantityFollowing;

    public UserDto(){
        super();
    }

    public UserDto(User user){
        id = user.getId();
        name = user.getName();
        image = user.getImage();
		quantityLikesNews = user.getLikedNews().size();
		quantityLikesComments = user.getLikedComments().size();
		quantityComments = user.getComments().size();
		quantitySaved = user.getSavedNews().size();
		quantityPosted = user.getPosted().size();
		quantityFollowing = user.getFollowing().size();
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}


	public int getQuantityLikesNews() {
		return this.quantityLikesNews;
	}

	public void setQuantityLikesNews(int quantityLikesNews) {
		this.quantityLikesNews = quantityLikesNews;
	}

	public int getQuantityLikesComments() {
		return this.quantityLikesComments;
	}

	public void setQuantityLikesComments(int quantityLikesComments) {
		this.quantityLikesComments = quantityLikesComments;
	}

	public int getQuantityComments() {
		return this.quantityComments;
	}

	public void setQuantityComments(int quantityComments) {
		this.quantityComments = quantityComments;
	}

	public int getQuantitySaved() {
		return this.quantitySaved;
	}

	public void setQuantitySaved(int quantitySaved) {
		this.quantitySaved = quantitySaved;
	}
	

	public int getQuantityPosted() {
		return this.quantityPosted;
	}

	public void setQuantityPosted(int quantityPosted) {
		this.quantityPosted = quantityPosted;
	}

	public int getQuantityFollowing() {
		return this.quantityFollowing;
	}

	public void setQuantityFollowing(int quantityFollowing) {
		this.quantityFollowing = quantityFollowing;
	}
    
    

}
