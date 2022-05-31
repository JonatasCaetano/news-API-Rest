package com.news.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.validation.Valid;

import com.news.api.models.entities.Company;
import com.news.api.models.entities.dtos.CompanyDto;
import com.news.api.models.entities.dtos.NewsDto;
import com.news.api.models.entities.dtos.UserDto;
import com.news.api.models.exceptions.CompanyInvalidException;
import com.news.api.models.exceptions.EmailException;
import com.news.api.models.exceptions.PasswordException;
import com.news.api.models.exceptions.UnauthorizedException;
import com.news.api.models.exceptions.UserInvalidException;
import com.news.api.services.CompanyService;

@RestController
@RequestMapping(value = "/companies")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@PostMapping(path = "/create")
	public ResponseEntity<String> createAccount(@RequestBody @Valid Company company) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(companyService.createAccount(company));
		} catch (NoSuchAlgorithmException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} 
	}

	@GetMapping(path = "/login")
	public ResponseEntity<String> login(@RequestParam(name = "email" ) String email, @RequestParam(name = "password") String password) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(companyService.login(email, password));
		} catch(PasswordException e){
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (EmailException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (NoSuchAlgorithmException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping(path = "/auth")
	public ResponseEntity<Company> login(@RequestHeader(name = "token") String token) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(companyService.isAuthorization(token));
		} catch (NoSuchAlgorithmException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (UnauthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (CompanyInvalidException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping(path = "/profile")
	public ResponseEntity<CompanyDto> getProfile(@RequestHeader(name = "token") String token) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(companyService.getProfile(token));
		} catch (NoSuchAlgorithmException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (UnauthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (CompanyInvalidException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping(path = "/posted")
	public ResponseEntity<List<NewsDto>> getPosted(@RequestHeader(name = "token") String token)  {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(companyService.getPosted(token));
		} catch (NoSuchAlgorithmException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (UnauthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (CompanyInvalidException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping(path = "/writers")
	public ResponseEntity<List<UserDto>> getCurrentWriters(@RequestHeader(name = "token") String token)  {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(companyService.getCurrentWriters(token));
		} catch (NoSuchAlgorithmException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (UnauthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (CompanyInvalidException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping(path = "/former")
	public ResponseEntity<List<UserDto>> getFormerWriters(@RequestHeader(name = "token") String token)  {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(companyService.getFormerWriters(token));
		} catch (NoSuchAlgorithmException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (UnauthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (CompanyInvalidException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping(path = "/followers")
	public ResponseEntity<List<UserDto>> getFollowers(@RequestHeader(name = "token") String token)  {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(companyService.getFollowers(token));
		} catch (NoSuchAlgorithmException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (UnauthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (CompanyInvalidException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PutMapping(path = "/writers/add/{userId}")
	public ResponseEntity<Void> addCurrentWriters(@RequestHeader(name = "token") String token, @PathVariable String userId){
		try {
			companyService.addCurrentWriters(token, userId);
			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
		} catch (NoSuchAlgorithmException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (UserInvalidException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (UnauthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (CompanyInvalidException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
	}

	@PutMapping(path = "/writers/remove/{userId}")
	public ResponseEntity<Void> removeCurrentWriters(@RequestHeader(name = "token") String token, @PathVariable String userId){
		try {
			companyService.removeCurrentWriters(token, userId);
			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
		} catch (NoSuchAlgorithmException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (UserInvalidException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (UnauthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (CompanyInvalidException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
	}



}
