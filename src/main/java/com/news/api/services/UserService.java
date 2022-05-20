package com.news.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.news.api.models.entities.User;
import com.news.api.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User createAccount(User user) {
		return userRepository.insert(user);
	}
	
	public User login(String email, String password) throws Exception {
		User user = userRepository.findByEmail(email).get();
		if(user.getPassword().equals(password)) {
			return user;
		}else {
			throw new Exception();
		}
	}
	

}
