package com.news.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import com.news.api.models.entities.Comment;
import com.news.api.models.entities.Company;
import com.news.api.models.entities.News;
import com.news.api.models.entities.User;
import com.news.api.models.entities.dtos.CommentDto;
import com.news.api.models.entities.dtos.NewsDto;
import com.news.api.models.entities.dtos.UserDto;
import com.news.api.models.exceptions.CompanyInvalidException;
import com.news.api.models.exceptions.InsufficientCredentialException;
import com.news.api.models.exceptions.NewsException;
import com.news.api.models.exceptions.UnauthorizedException;
import com.news.api.models.exceptions.UserInvalidException;
import com.news.api.repositories.NewsRepository;

@Service
public class NewsService {

	@Autowired
	private NewsRepository newsRepository;

	@Autowired
	@Lazy
	private UserService userService;

	@Autowired
	@Lazy
	private CompanyService companyService;


	public NewsDto getNews(String id){
		return newsRepository.findById(id).get().toNewsDto();
	}

	public NewsDto createNews(String token, News news, String companyId) throws NoSuchAlgorithmException, UnauthorizedException, UserInvalidException, CompanyInvalidException, InsufficientCredentialException{
		news.setCreationDate(LocalDateTime.now(ZoneOffset.UTC));
		news.setVisible(true);
		news.setAuthor(userService.isAuthorization(token));
		Optional<Company> optional = companyService.findById(companyId);
		if(optional.isPresent()){
			if(optional.get().getCurrentWriters().contains(news.getAuthor())){
				news.setPublisher(optional.get());
				newsRepository.insert(news);
				userService.addPosted(news.getAuthor(), news);
				companyService.addPosted(news.getPublisher(), news);
				return news.toNewsDto();
			}else{
				throw new InsufficientCredentialException();
			}
		}else{
			throw new CompanyInvalidException();
		}
	}

	public NewsDto editNews(String token, News news) throws NoSuchAlgorithmException, UnauthorizedException, UserInvalidException, CompanyInvalidException, InsufficientCredentialException{
		Optional<News> optional = newsRepository.findById(news.getId());
		if(optional.isPresent()){
			News obj = optional.get();
			if(obj.getPublisher().getCurrentWriters().contains(userService.isAuthorization(token))){
				obj.setTitle(news.getTitle());
				obj.setBody(news.getBody());
				obj.setImage(news.getImage());
				return newsRepository.save(obj).toNewsDto();
			}else{
				throw new InsufficientCredentialException();
			}
		}else{
			throw new CompanyInvalidException();
		}
	}

	public NewsDto putLike(String token, String newsId) throws NoSuchAlgorithmException, UnauthorizedException, UserInvalidException, NewsException{
		User user = userService.isAuthorization(token);
		Optional<News> optional = newsRepository.findById(newsId);
		if(optional.isPresent()){
				userService.putLike(user, optional.get());
				return newsRepository.save(optional.get().putLike(user)).toNewsDto();
		}else{
			throw new NewsException();
		}
	}

	public List<CommentDto> getComments(String newsId) throws NewsException{
		Optional<News> optional = newsRepository.findById(newsId);
		if(optional.isPresent()){
			return optional.get().getComments().stream().map(Comment::toCommentDto).toList();
		}else{
			throw new NewsException();
		}
	}

	public List<UserDto> getLikes(String newsId) throws NewsException{
		Optional<News> optional = newsRepository.findById(newsId);
		if(optional.isPresent()){
			return optional.get().getLikes().stream().map(User::toUserDto).toList();
		}else{
			throw new NewsException();
		}
	}

	public NewsDto changeVisibility(String token, String newsId) throws NewsException, NoSuchAlgorithmException, UnauthorizedException, UserInvalidException, InsufficientCredentialException{
		Optional<News> optional = newsRepository.findById(newsId);
		if(optional.isPresent()){
			News news = optional.get();
			if(news.getPublisher().getCurrentWriters().contains(userService.isAuthorization(token))){
				return newsRepository.save(news.putVisibility()).toNewsDto();
			}else{
				throw new InsufficientCredentialException();
			}
			
		}else{
			throw new NewsException();
		}
	}

	//Internal methods
	public Optional<News> findById(String id){
		return newsRepository.findById(id);
	}

	public void addComment(News news, Comment comment){
		newsRepository.save(news.addComment(comment));
	}

	public void removeComment(News news, Comment comment){
		newsRepository.save(news.removeComment(comment));
	}

	

}
