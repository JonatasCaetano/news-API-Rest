package com.news.api.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.news.api.models.entities.Comment;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

}
