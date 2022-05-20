package com.news.api.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.news.api.models.entities.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	public Optional<User> findByEmail(String email);
}
