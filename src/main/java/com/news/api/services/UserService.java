package com.news.api.services;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.news.api.models.entities.User;
import com.news.api.models.entities.dtos.UserDto;
import com.news.api.models.entities.dtos.CompanyDto;
import com.news.api.models.entities.dtos.NewsDto;
import com.news.api.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public String createAccount(User user) {
		user.setCreationDate(LocalDateTime.now(ZoneOffset.UTC));
		user = userRepository.insert(user);
		user.setLastLogin(LocalDateTime.now(ZoneOffset.UTC));
		userRepository.save(user);
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
		User user = Authorization.isAuthorization(token, userRepository);
		return new UserDto(user);
	}

	public List<NewsDto> getSavedNews(String token) throws Exception{
		User user = Authorization.isAuthorization(token, userRepository);
		List<NewsDto> newsDtos = new ArrayList<>();
		user.getSavedNews().forEach(news -> {
			NewsDto newDto = new NewsDto(news);
			newsDtos.add(newDto);
		});
		return newsDtos;
	}

	public List<NewsDto> getPosted(String token) throws Exception{
		User user = Authorization.isAuthorization(token, userRepository);
		List<NewsDto> newsDtos = new ArrayList<>();
		user.getPosted().forEach(news -> {
			NewsDto newDto = new NewsDto(news);
			newsDtos.add(newDto);
		});
		return newsDtos;
	}

	public List<CompanyDto> getCurrentJob(String token) throws Exception{
		User user = Authorization.isAuthorization(token, userRepository);
		List<CompanyDto> companyDtos = new ArrayList<>();
		user.getCurrentJob().forEach(company -> {
			CompanyDto companyDto = new CompanyDto(company);
			companyDtos.add(companyDto);
		});
		return companyDtos;
	}

	public List<CompanyDto> getHasWorked(String token) throws Exception{
		User user = Authorization.isAuthorization(token, userRepository);
		List<CompanyDto> companyDtos = new ArrayList<>();
		user.getHasWorked().forEach(company -> {
			CompanyDto companyDto = new CompanyDto(company);
			companyDtos.add(companyDto);
		});
		return companyDtos;
	}






}
