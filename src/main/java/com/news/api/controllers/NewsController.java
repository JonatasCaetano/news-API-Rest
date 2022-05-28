package com.news.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

import com.news.api.models.entities.News;
import com.news.api.models.entities.dtos.NewsDto;
import com.news.api.models.exceptions.CompanyInvalidException;
import com.news.api.models.exceptions.InsufficientCredentialException;
import com.news.api.models.exceptions.UnauthorizedException;
import com.news.api.models.exceptions.UserInvalidException;
import com.news.api.services.NewsService;

@RestController
@RequestMapping(value = "/news")
public class NewsController {

	@Autowired
	private NewsService newsService;

	@GetMapping(path = "/{id}")
	public ResponseEntity<NewsDto> getNews(@PathVariable String id){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(newsService.getNews(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PostMapping(path = "/create/{companyId}")
	public ResponseEntity<NewsDto> createNews(@RequestHeader(name = "token") String token, @PathVariable String companyId, @RequestBody News news){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(newsService.createNews(token, news, companyId));
		} catch (NoSuchAlgorithmException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (UnauthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (UserInvalidException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (CompanyInvalidException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (InsufficientCredentialException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
	}

}
