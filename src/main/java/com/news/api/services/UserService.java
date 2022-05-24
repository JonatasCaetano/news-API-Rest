package com.news.api.services;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.news.api.models.entities.Company;
import com.news.api.models.entities.News;
import com.news.api.models.entities.User;
import com.news.api.models.entities.dtos.UserDto;
import com.news.api.models.entities.dtos.CompanyDto;
import com.news.api.models.entities.dtos.NewsDto;
import com.news.api.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	@Lazy
	private CompanyService companyService;
	
	public String createAccount(User user) {
		user.setCreationDate(LocalDateTime.now(ZoneOffset.UTC));
		user.setLastLogin(LocalDateTime.now(ZoneOffset.UTC));
		user = userRepository.insert(user);
		return Authorization.login(user);
	}
	
	public String login(String email, String password) throws Exception {
		User user = userRepository.findByEmail(email).get();
		if(user.getPassword().equals(password)) {
			user.setLastLogin(LocalDateTime.now(ZoneOffset.UTC));
			userRepository.save(user);
			return Authorization.login(user);
		}else {
			throw new Exception();
		}
	}
	
	public User isAuthorization(String token) throws Exception {
		return Authorization.isAuthorization(token, userRepository);
	}

	public UserDto getProfile(String token) throws Exception{
		return Authorization.isAuthorization(token, userRepository).toUserDto();
	}

	public List<NewsDto> getSavedNews(String token) throws Exception{
		return Authorization.isAuthorization(token, userRepository).getSavedNews().stream().map(News::toNewsDto).toList();
	}

	public List<NewsDto> getPosted(String token) throws Exception{
		return Authorization.isAuthorization(token, userRepository).getPosted().stream().map(News::toNewsDto).toList();
	}

	public List<CompanyDto> getCurrentJob(String token) throws Exception{
		return Authorization.isAuthorization(token, userRepository).getCurrentJob().stream().map(Company::toCompanyDto).toList();
	}

	public List<CompanyDto> getHasWorked(String token) throws Exception{
		return Authorization.isAuthorization(token, userRepository).getHasWorked().stream().map(Company::toCompanyDto).toList();
	}

	public List<CompanyDto> getFollowing(String token) throws Exception{
		return Authorization.isAuthorization(token, userRepository).getFollowing().stream().map(Company::toCompanyDto).toList();
	}

	public void addFollowing(String token, String id) throws Exception{
		companyService.addFollower(userRepository.save(Authorization.isAuthorization(token, userRepository).addFollowing(companyService.findById(id).get())), id);
	}

	public void removeFollowing(String token, String id) throws Exception{
		companyService.removeFollower(userRepository.save(Authorization.isAuthorization(token, userRepository).removeFollowing(companyService.findById(id).get())), id);
	}



}
