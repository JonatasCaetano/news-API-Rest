package com.news.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.List;

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

import com.news.api.models.entities.User;
import com.news.api.models.entities.dtos.CommentDto;
import com.news.api.models.entities.dtos.CompanyDto;
import com.news.api.models.entities.dtos.NewsDto;
import com.news.api.models.entities.dtos.UserDto;
import com.news.api.models.exceptions.EmailException;
import com.news.api.models.exceptions.CompanyInvalidException;
import com.news.api.models.exceptions.PasswordException;
import com.news.api.models.exceptions.UnauthorizedException;
import com.news.api.models.exceptions.UserInvalidException;
import com.news.api.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping(path = "/create")
	public ResponseEntity<String> createAccount(@RequestBody User user) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(userService.createAccount(user));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} 
	}
	
	@GetMapping(path = "/login")
	public ResponseEntity<String> login(@RequestParam(name = "email" ) String email, @RequestParam(name = "password") String password) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.login(email, password));
		} catch(PasswordException e){
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (EmailException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (NoSuchAlgorithmException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping(path = "/auth")
	public ResponseEntity<User> login(@RequestHeader(name = "token") String token) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.isAuthorization(token));
		} catch (NoSuchAlgorithmException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (UnauthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (UserInvalidException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	
	}

	@GetMapping(path = "/profile")
	public ResponseEntity<UserDto> getProfile(@RequestHeader(name = "token") String token) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.getProfile(token));
		} catch (NoSuchAlgorithmException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (UnauthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (UserInvalidException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping(path = "/saved")
	public ResponseEntity<List<NewsDto>> getSavedNews(@RequestHeader(name = "token") String token) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.getSavedNews(token));
		} catch (NoSuchAlgorithmException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (UnauthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (UserInvalidException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping(path = "/posted")
	public ResponseEntity<List<NewsDto>> getPosted(@RequestHeader(name = "token") String token)  {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.getPosted(token));
		} catch (NoSuchAlgorithmException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (UnauthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (UserInvalidException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping(path = "/jobs")
	public ResponseEntity<List<CompanyDto>> getCurrentJob(@RequestHeader(name = "token") String token){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.getCurrentJobs(token));
		} catch (NoSuchAlgorithmException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (UnauthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (UserInvalidException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@GetMapping(path = "/worked")
	public ResponseEntity<List<CompanyDto>> getHasWorked(@RequestHeader(name = "token") String token){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.getHasWorked(token));
		} catch (NoSuchAlgorithmException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (UnauthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (UserInvalidException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping(path = "/following")
	public ResponseEntity<List<CompanyDto>> getFollowing(@RequestHeader(name = "token") String token){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.getFollowing(token));
		} catch (NoSuchAlgorithmException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (UnauthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (UserInvalidException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PutMapping(path = "/following/add/{CompanyId}")
	public ResponseEntity<Void> addFollowing(@RequestHeader(name = "token") String token, @PathVariable String CompanyId){
		try {
			userService.addFollowing(token, CompanyId);
			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
		} catch (NoSuchAlgorithmException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (UnauthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (CompanyInvalidException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (UserInvalidException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
	}

	@PutMapping(path = "/following/remove/{CompanyId}")
	public ResponseEntity<Void> removeFollowing(@RequestHeader(name = "token") String token, @PathVariable String CompanyId){
		try {
			userService.removeFollowing(token, CompanyId);
			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
		} catch (NoSuchAlgorithmException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (UnauthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (CompanyInvalidException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (UserInvalidException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
	}

	@GetMapping(path = "/comments")
	public ResponseEntity<List<CommentDto>> getComments(@RequestHeader(name = "token") String token){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.getComments(token));
		} catch (NoSuchAlgorithmException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (UnauthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (UserInvalidException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

}
