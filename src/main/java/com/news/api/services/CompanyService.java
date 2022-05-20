package com.news.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.news.api.repositories.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
}
