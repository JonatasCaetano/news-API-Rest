package com.news.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
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
import com.news.api.models.exceptions.CompanyInvalidException;
import com.news.api.models.exceptions.EmailException;
import com.news.api.models.exceptions.PasswordException;
import com.news.api.models.exceptions.UnauthorizedException;
import com.news.api.models.exceptions.UserInvalidException;
import com.news.api.repositories.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	@Lazy
	private UserService userService;

	public String createAccount(Company company) throws NoSuchAlgorithmException {
		company.setCreationDate(LocalDateTime.now(ZoneOffset.UTC));
		company.setLastLogin(LocalDateTime.now(ZoneOffset.UTC));
		company = companyRepository.insert(company);
		return AuthorizationService.login(company);
	}

	public String login(String email, String password) throws PasswordException, EmailException, NoSuchAlgorithmException {
		
		Optional<Company> optional = companyRepository.findByEmail(email);
		
		if(!optional.isPresent()){
			throw new EmailException();
		}
		Company company = optional.get();

		if(company.isPassword(password)) {
			company.setLastLogin(LocalDateTime.now(ZoneOffset.UTC));
			companyRepository.save(company);
			return AuthorizationService.login(company);
		}else {
			throw new PasswordException();
		}
	}
	
	public Company isAuthorization(String token) throws NoSuchAlgorithmException, UnauthorizedException, CompanyInvalidException {
		return AuthorizationService.isAuthorization(token, companyRepository);
	}

	public CompanyDto getProfile(String token) throws NoSuchAlgorithmException, UnauthorizedException, CompanyInvalidException{
		return AuthorizationService.isAuthorization(token, companyRepository).toCompanyDto();
	}

	public List<NewsDto> getPosted(String token) throws NoSuchAlgorithmException, UnauthorizedException, CompanyInvalidException{
		return AuthorizationService.isAuthorization(token, companyRepository).getPosted().stream().map(News::toNewsDto).toList();
	}

	public List<UserDto> getCurrentWriters(String token) throws NoSuchAlgorithmException, UnauthorizedException, CompanyInvalidException{
		return AuthorizationService.isAuthorization(token, companyRepository).getCurrentWriters().stream().map(User::toUserDto).toList();
	}

	public List<UserDto> getFormerWriters(String token) throws NoSuchAlgorithmException, UnauthorizedException, CompanyInvalidException{
		return AuthorizationService.isAuthorization(token, companyRepository).getFormerWriters().stream().map(User::toUserDto).toList();
	}

	public List<UserDto> getFollowers(String token) throws NoSuchAlgorithmException, UnauthorizedException, CompanyInvalidException{
		return AuthorizationService.isAuthorization(token, companyRepository).getFollowers().stream().map(User::toUserDto).toList();
	}

	public void addCurrentWriters(String token, String userId) throws UserInvalidException, NoSuchAlgorithmException, UnauthorizedException, CompanyInvalidException{
		Optional<User> optional = userService.findById(userId);
		if(!optional.isPresent()){
			throw new UserInvalidException();
		}
		userService.addCurrentJobs(optional.get(), companyRepository.save(AuthorizationService.isAuthorization(token, companyRepository).addCurrentWriters(optional.get())).getId());
		addFormerWriters(token, userId);
	}

	public void removeCurrentWriters(String token, String userId) throws UserInvalidException, NoSuchAlgorithmException, UnauthorizedException, CompanyInvalidException{
		Optional<User> optional = userService.findById(userId);
		if(!optional.isPresent()){
			throw new UserInvalidException();
		}
		userService.removeCurrentJobs(optional.get(), companyRepository.save(AuthorizationService.isAuthorization(token, companyRepository).removeCurrentWriters(optional.get())).getId());
	}

	//Internal methods

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

	public void addFormerWriters(String token, String userId) throws UserInvalidException, NoSuchAlgorithmException, UnauthorizedException, CompanyInvalidException{
		Optional<User> optional = userService.findById(userId);
		if(!optional.isPresent()){
			throw new UserInvalidException();
		}
		userService.addHasWorked(optional.get(), companyRepository.save(AuthorizationService.isAuthorization(token, companyRepository).addFormerWriters(optional.get())).getId());
	}



}
