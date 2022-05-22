package com.news.api.models.entities.dtos;

import com.news.api.models.entities.Company;

public class CompanyDto {

    private String id;
	private String name;
    private String image;

    public CompanyDto(){
        super();
    }

    public CompanyDto(Company company){
        id = company.getId();
        name = company.getName();
        image = company.getImage();
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
