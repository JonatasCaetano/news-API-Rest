package com.news.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import com.news.api.models.entities.Company;
import com.news.api.models.entities.dtos.CompanyDto;
import com.news.api.models.entities.dtos.NewsDto;
import com.news.api.models.entities.dtos.UserDto;
import com.news.api.repositories.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	public String createAccount(Company company) {
		company.setCreationDate(LocalDateTime.now(ZoneOffset.UTC));
		company.setLastLogin(LocalDateTime.now(ZoneOffset.UTC));
		company = companyRepository.insert(company);
		return Authorization.login(company);
	}

	public String login(String email, String password) throws Exception {
		Company company = companyRepository.findByEmail(email).get();
		if(company.getPassword().equals(password)) {
			company.setLastLogin(LocalDateTime.now(ZoneOffset.UTC));
			companyRepository.save(company);
			return Authorization.login(company);
		}else {
			throw new Exception();
		}
	}
	
	public Company isAuthorization(String token) throws Exception {
		return Authorization.isAuthorization(token, companyRepository);
	}

	public CompanyDto getProfile(String token) throws Exception{
		return Authorization.isAuthorization(token, companyRepository).toCompanyDto();
	}

	public List<NewsDto> getPosted(String token) throws Exception{
		List<NewsDto> newsDtos = new ArrayList<>();
		Authorization.isAuthorization(token, companyRepository).getPosted().forEach(news->newsDtos.add(news.toNewsDto()));;
		return newsDtos;
	}

	public List<UserDto> getCurrentWriters(String token) throws Exception{
		List<UserDto> userDtos = new ArrayList<>();
		Authorization.isAuthorization(token, companyRepository).getCurrentWriters().forEach(user->userDtos.add(user.toUserDto()));;
		return userDtos;
	}

	public List<UserDto> getFormerWriters(String token) throws Exception{
		List<UserDto> userDtos = new ArrayList<>();
		Authorization.isAuthorization(token, companyRepository).getFormerWriters().forEach(user->userDtos.add(user.toUserDto()));;
		return userDtos;
	}

	public List<UserDto> getFollowers(String token) throws Exception{
		List<UserDto> userDtos = new ArrayList<>();
		Authorization.isAuthorization(token, companyRepository).getFollowers().forEach(user->userDtos.add(user.toUserDto()));;
		return userDtos;
	}




}
