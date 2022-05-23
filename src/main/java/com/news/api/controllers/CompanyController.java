package com.news.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.news.api.models.entities.Company;
import com.news.api.services.CompanyService;

@RestController
@RequestMapping(value = "/companies")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@PostMapping(path = "/create")
	public ResponseEntity<String> createAccount(@RequestBody Company company) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(companyService.createAccount(company));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} 
	}

	

}
