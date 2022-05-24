package com.news.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.news.api.models.entities.dtos.NewsDto;
import com.news.api.repositories.NewsRepository;

@Service
public class NewsService {

	@Autowired
	private NewsRepository newsRepository;

	public NewsDto getNews(String id){
		return newsRepository.findById(id).get().toNewsDto();
	}








}
