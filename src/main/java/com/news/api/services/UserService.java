package com.news.api.services;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.news.api.models.entities.User;
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
	
	
}
