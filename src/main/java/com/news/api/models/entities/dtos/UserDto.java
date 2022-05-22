package com.news.api.models.entities.dtos;

import com.news.api.models.entities.User;

public class UserDto {
    
    private String id;
	private String name;
    private String image;

    public UserDto(){
        super();
    }

    public UserDto(User user){
        id = user.getId();
        name = user.getName();
        image = user.getImage();
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

	
    
    

}
