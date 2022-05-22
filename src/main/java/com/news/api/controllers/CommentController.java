package com.news.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.news.api.services.CommentService;

@RestController
@RequestMapping(value = "/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;
}