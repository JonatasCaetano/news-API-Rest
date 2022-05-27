package com.news.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.news.api.models.entities.Comment;
import com.news.api.models.entities.dtos.CommentDto;
import com.news.api.models.exceptions.UnauthorizedException;
import com.news.api.models.exceptions.UserInvalidException;
import com.news.api.repositories.CommentRepository;
import com.news.api.repositories.UserRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	@Lazy
	private UserRepository userRepository;

	public CommentDto getComment(String id){
		return commentRepository.findById(id).get().toCommentDto();
	}

	public CommentDto createComment(String token, Comment comment) throws NoSuchAlgorithmException, UnauthorizedException, UserInvalidException{
		return commentRepository.insert(new Comment(comment.getBody(), LocalDateTime.now(ZoneOffset.UTC), AuthorizationService.isAuthorization(token, userRepository))).toCommentDto();
	}

	

}
