package com.news.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import com.news.api.models.entities.Comment;
import com.news.api.models.entities.News;
import com.news.api.models.entities.User;
import com.news.api.models.entities.dtos.CommentDto;
import com.news.api.models.exceptions.CommentException;
import com.news.api.models.exceptions.NewsException;
import com.news.api.models.exceptions.UnauthorizedException;
import com.news.api.models.exceptions.UserInvalidException;
import com.news.api.repositories.CommentRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	@Lazy
	private UserService userService;

	@Autowired
	@Lazy
	private NewsService newsService;

	public CommentDto getComment(String id){
		return commentRepository.findById(id).get().toCommentDto();
	}

	public CommentDto createComment(String token, String newsId, Comment comment) throws NoSuchAlgorithmException, UnauthorizedException, UserInvalidException, NewsException{
		User user = userService.isAuthorization(token);
		Optional<News> optional = newsService.findById(newsId);
		if(!optional.isPresent()){
			throw new NewsException();
		}
		Comment obj = commentRepository.insert(new Comment(comment.getBody(), LocalDateTime.now(ZoneOffset.UTC), userService.isAuthorization(token), optional.get()));
		newsService.addComment(optional.get(), obj);
		userService.addComment(user, obj);
		return obj.toCommentDto();
	}

	public void deleteComment(String token, String commentId) throws NoSuchAlgorithmException, UnauthorizedException, UserInvalidException, CommentException, NewsException{
		Optional<Comment> optional = commentRepository.findById(commentId);
		if(optional.isPresent()){
			if(optional.get().getAuthor().equals(userService.isAuthorization(token))){
				newsService.removeComment(optional.get().getNews(), optional.get());
				userService.removeComment(userService.isAuthorization(token), optional.get());
				commentRepository.delete(optional.get());
			}else{
				throw new UnauthorizedException();
			}
		}else{
			throw new CommentException();
		}
	}

	public CommentDto editComment(String token, String commentId, Comment comment) throws NoSuchAlgorithmException, UnauthorizedException, UserInvalidException, CommentException{
		User user = userService.isAuthorization(token);
		Optional<Comment> optional = commentRepository.findById(commentId);
		if(optional.isPresent()){
			if(optional.get().getAuthor().equals(user)){
				Comment obj = optional.get();
				obj.setBody(comment.getBody());
				return commentRepository.save(obj).toCommentDto();
			}else{
				throw new UnauthorizedException();
			}
		}else{
			throw new CommentException();
		}
	}

	public CommentDto putLike(String token, String commentId) throws NoSuchAlgorithmException, UnauthorizedException, UserInvalidException, CommentException{
		User user = userService.isAuthorization(token);
		Optional<Comment> optional = commentRepository.findById(commentId);
		if(optional.isPresent()){
				userService.putLike(user, optional.get());
				return commentRepository.save(optional.get().putLike(user)).toCommentDto();
		}else{
			throw new CommentException();
		}
	}

}
