package com.news.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.news.api.repositories.CommentRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;
}
