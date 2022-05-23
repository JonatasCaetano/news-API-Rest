package com.news.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.news.api.models.entities.Company;
import com.news.api.repositories.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	public String createAccount(Company company) {
		company.setCreationDate(LocalDateTime.now(ZoneOffset.UTC));
		company.setLastLogin(LocalDateTime.now(ZoneOffset.UTC));
		company = companyRepository.insert(company);
		return Authorization.login(company);
	}

	public String login(String email, String password) throws Exception {
		Company company = companyRepository.findByEmail(email).get();
		if(company.getPassword().equals(password)) {
			company.setLastLogin(LocalDateTime.now(ZoneOffset.UTC));
			companyRepository.save(company);
			return Authorization.login(company);
		}else {
			throw new Exception();
		}
	}
	
	public Company isAuthorization(String token) throws Exception {
		return Authorization.isAuthorization(token, companyRepository);
	}
}
