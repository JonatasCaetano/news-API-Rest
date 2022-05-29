package com.news.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

import com.news.api.models.entities.Comment;
import com.news.api.models.entities.dtos.CommentDto;
import com.news.api.models.exceptions.CommentException;
import com.news.api.models.exceptions.NewsException;
import com.news.api.models.exceptions.UnauthorizedException;
import com.news.api.models.exceptions.UserInvalidException;
import com.news.api.services.CommentService;

@RestController
@RequestMapping(value = "/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@GetMapping(path = "/{id}")
	public ResponseEntity<CommentDto> getComment(@PathVariable String id){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(commentService.getComment(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PostMapping(path = "/create/{newsId}")
	public ResponseEntity<CommentDto> createComment(@RequestHeader(name = "token") String token, @PathVariable String newsId, @RequestBody Comment comment){
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(token, newsId, comment));
		} catch (NoSuchAlgorithmException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (UnauthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (UserInvalidException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (NewsException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@DeleteMapping(path = "/{commentId}")
	public ResponseEntity<CommentDto> deleteComment(@RequestHeader(name = "token") String token, @PathVariable String commentId){
		try {
			commentService.deleteComment(token, commentId);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (NoSuchAlgorithmException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (UnauthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (UserInvalidException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (CommentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (NewsException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PutMapping(path = "/{commentId}")
	public ResponseEntity<CommentDto> editComment(@RequestHeader(name = "token") String token, @PathVariable String commentId, @RequestBody 
	Comment comment){
		try {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(commentService.editComment(token, commentId, comment));
		} catch (NoSuchAlgorithmException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (UnauthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (UserInvalidException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (CommentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PutMapping(path = "like/{commentId}")
	public ResponseEntity<CommentDto> putLike(@RequestHeader(name = "token") String token, @PathVariable String commentId){
		try {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(commentService.putLike(token, commentId));
		} catch (NoSuchAlgorithmException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (UnauthorizedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (UserInvalidException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (CommentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
