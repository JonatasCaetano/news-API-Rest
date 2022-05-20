package com.news.api.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.news.api.models.entities.News;

@Repository
public interface NewsRepository extends MongoRepository<News, String> {

}
