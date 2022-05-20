package com.news.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.news.api.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
}
