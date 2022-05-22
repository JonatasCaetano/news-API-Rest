package com.news.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.news.api.models.entities.User;
import com.news.api.models.entities.dtos.UserDto;
import com.news.api.models.entities.dtos.NewsDto;
import com.news.api.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping(path = "/create")
	public ResponseEntity<String> createAccount(@RequestBody User user) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.createAccount(user));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} 
	}
	
	@GetMapping(path = "/login")
	public ResponseEntity<String> login(@RequestParam(name = "email" ) String email, @RequestParam(name = "password") String password) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.login(email, password));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	@GetMapping(path = "/auth")
	public ResponseEntity<User> login(@RequestHeader(name = "token") String token) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.isAuthorization(token));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@GetMapping(path = "/profile")
	public ResponseEntity<UserDto> getProfile(@RequestHeader(name = "token") String token) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.getProfile(token));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@GetMapping(path = "/saved")
	public ResponseEntity<List<NewsDto>> getSavedNews(@RequestHeader(name = "token") String token) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.getSavedNews(token));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
}
