package com.news.api.services;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.news.api.models.entities.Company;
import com.news.api.models.entities.News;
import com.news.api.models.entities.User;
import com.news.api.models.entities.dtos.UserDto;
import com.news.api.models.exceptions.EmailException;
import com.news.api.models.exceptions.CompanyInvalidException;
import com.news.api.models.exceptions.PasswordException;
import com.news.api.models.exceptions.UnauthorizedException;
import com.news.api.models.exceptions.UserInvalidException;
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
	
	public String createAccount(User user) throws NoSuchAlgorithmException {
		user.setCreationDate(LocalDateTime.now(ZoneOffset.UTC));
		user.setLastLogin(LocalDateTime.now(ZoneOffset.UTC));
		user = userRepository.insert(user);
		return AuthorizationService.login(user);
	}
	
	public String login(String email, String password) throws PasswordException, EmailException, NoSuchAlgorithmException {
		Optional<User> optional = userRepository.findByEmail(email);
		
		if(!optional.isPresent()){
			throw new EmailException();
		}
		User user = optional.get();
		if(user.isPassword(password)) {
			user.setLastLogin(LocalDateTime.now(ZoneOffset.UTC));
			userRepository.save(user);
			return AuthorizationService.login(user);
		}else {
			throw new PasswordException();
		}
	}
	
	public User isAuthorization(String token) throws NoSuchAlgorithmException, UnauthorizedException, UserInvalidException{
		return AuthorizationService.isAuthorization(token, userRepository);
	}

	public UserDto getProfile(String token) throws NoSuchAlgorithmException, UnauthorizedException, UserInvalidException{
		return AuthorizationService.isAuthorization(token, userRepository).toUserDto();
	}

	public List<NewsDto> getSavedNews(String token) throws NoSuchAlgorithmException, UnauthorizedException, UserInvalidException{
		return AuthorizationService.isAuthorization(token, userRepository).getSavedNews().stream().map(News::toNewsDto).toList();
	}

	public List<NewsDto> getPosted(String token) throws NoSuchAlgorithmException, UnauthorizedException, UserInvalidException{
		return AuthorizationService.isAuthorization(token, userRepository).getPosted().stream().map(News::toNewsDto).toList();
	}

	public List<CompanyDto> getCurrentJobs(String token) throws NoSuchAlgorithmException, UnauthorizedException, UserInvalidException{
		return AuthorizationService.isAuthorization(token, userRepository).getCurrentJobs().stream().map(Company::toCompanyDto).toList();
	}

	public List<CompanyDto> getHasWorked(String token) throws NoSuchAlgorithmException, UnauthorizedException, UserInvalidException{
		return AuthorizationService.isAuthorization(token, userRepository).getHasWorked().stream().map(Company::toCompanyDto).toList();
	}

	public List<CompanyDto> getFollowing(String token) throws NoSuchAlgorithmException, UnauthorizedException, UserInvalidException{
		return AuthorizationService.isAuthorization(token, userRepository).getFollowing().stream().map(Company::toCompanyDto).toList();
	}

	public void addFollowing(String token, String id) throws NoSuchAlgorithmException, UnauthorizedException, CompanyInvalidException, UserInvalidException{
		Optional<Company> optional = companyService.findById(id);
		if(optional.isPresent()){
			companyService.addFollower(userRepository.save(AuthorizationService.isAuthorization(token, userRepository).addFollowing(companyService.findById(id).get())), id);
		}else{
			throw new CompanyInvalidException();
		}
	}

	public void removeFollowing(String token, String id) throws NoSuchAlgorithmException, UnauthorizedException, CompanyInvalidException, UserInvalidException{
		Optional<Company> optional = companyService.findById(id);
		if(optional.isPresent()){
			companyService.removeFollower(userRepository.save(AuthorizationService.isAuthorization(token, userRepository).removeFollowing(companyService.findById(id).get())), id);
		}else{
			throw new CompanyInvalidException();
		}	
	}

	//Internal methods

	public Optional<User> findById(String id){
		return userRepository.findById(id);
	}

	public void addCurrentJobs(User user, String companyId) throws UserInvalidException, NoSuchAlgorithmException, UnauthorizedException, CompanyInvalidException{
		Optional<Company> optional = companyService.findById(companyId);
		if(!optional.isPresent()){
			throw new UserInvalidException();
		}
		userRepository.save(user.addCurrentJobs(optional.get()));
	}

	public void removeCurrentJobs(User user, String companyId) throws UserInvalidException, NoSuchAlgorithmException, UnauthorizedException, CompanyInvalidException{
		Optional<Company> optional = companyService.findById(companyId);
		if(!optional.isPresent()){
			throw new UserInvalidException();
		}
		userRepository.save(user.removeCurrentJobs(optional.get()));
	}

	public void addHasWorked(User user, String companyId) throws UserInvalidException, NoSuchAlgorithmException, UnauthorizedException, CompanyInvalidException{
		Optional<Company> optional = companyService.findById(companyId);
		if(!optional.isPresent()){
			throw new UserInvalidException();
		}
		userRepository.save(user.addHasWorked(optional.get()));
	}

	public void removeHasWorked(User user, String companyId) throws UserInvalidException, NoSuchAlgorithmException, UnauthorizedException, CompanyInvalidException{
		Optional<Company> optional = companyService.findById(companyId);
		if(!optional.isPresent()){
			throw new UserInvalidException();
		}
		userRepository.save(user.removeHasWorked(optional.get()));
	}


}
