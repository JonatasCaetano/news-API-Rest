package com.news.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import com.news.api.models.entities.Company;
import com.news.api.models.entities.News;
import com.news.api.models.entities.User;
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
		return Authorization.isAuthorization(token, companyRepository).getPosted().stream().map(News::toNewsDto).toList();
	}

	public List<UserDto> getCurrentWriters(String token) throws Exception{
		return Authorization.isAuthorization(token, companyRepository).getCurrentWriters().stream().map(User::toUserDto).toList();
	}

	public List<UserDto> getFormerWriters(String token) throws Exception{
		return Authorization.isAuthorization(token, companyRepository).getFormerWriters().stream().map(User::toUserDto).toList();
	}

	public List<UserDto> getFollowers(String token) throws Exception{
		return Authorization.isAuthorization(token, companyRepository).getFollowers().stream().map(User::toUserDto).toList();
	}

	public void addFollower(User user, String id){
		companyRepository.save(companyRepository.findById(id).get().addFollower(user));
	
	}

	public void removeFollower(User user, String id){
		companyRepository.save(companyRepository.findById(id).get().removeFollower(user));
	
	}

	public Company save(Company company){
		return companyRepository.save(company);
	}

	public Optional<Company> findById(String id){
		return companyRepository.findById(id);
	}
}
