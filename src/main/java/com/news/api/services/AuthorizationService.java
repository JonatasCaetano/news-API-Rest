package com.news.api.services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.news.api.models.entities.Company;
import com.news.api.models.entities.User;
import com.news.api.models.exceptions.CompanyInvalidException;
import com.news.api.models.exceptions.UnauthorizedException;
import com.news.api.models.exceptions.UserInvalidException;
import com.news.api.repositories.CompanyRepository;
import com.news.api.repositories.UserRepository;

@Service
public class AuthorizationService {
	
	public static String login(User user) throws NoSuchAlgorithmException {
		String code = new String(user.getEmail() + user.getPassword() + LocaDateTimeFormatterService.formatDate(user.getLastLogin()) + "bananasDePijamas");
		final byte[] hash = MessageDigest.getInstance("SHA-256").digest(code.getBytes(StandardCharsets.UTF_8));
		final StringBuilder hashStr = new StringBuilder(hash.length);
		for (byte hashByte : hash)
			hashStr.append(Integer.toHexString(255 & hashByte));
			return new String(user.getId() + "." + hashStr.toString());

	}

	public static String login(Company company) throws NoSuchAlgorithmException {
		String code = new String(company.getEmail() + company.getPassword() + LocaDateTimeFormatterService.formatDate(company.getLastLogin()) + "bananasDePijamas");
		final byte[] hash = MessageDigest.getInstance("SHA-256").digest(code.getBytes(StandardCharsets.UTF_8));
		final StringBuilder hashStr = new StringBuilder(hash.length);
		for (byte hashByte : hash)
		hashStr.append(Integer.toHexString(255 & hashByte));
			return new String(company.getId() + "." + hashStr.toString());

	}

	public static User isAuthorization(String token, UserRepository userRepository) throws UnauthorizedException, NoSuchAlgorithmException, UserInvalidException {
			String[] data = token.split(Pattern.quote("."));
			Optional<User> optional = userRepository.findById(data[0]);
			if(optional.isPresent()){
				String[] codeVector = login(optional.get()).split(Pattern.quote("."));
				if(data[1].equals(codeVector[1])) {
					return optional.get();
				}else {
					throw new UnauthorizedException();
				}
			}else{
				throw new UserInvalidException();
			}
			
	}

	public static Company isAuthorization(String token, CompanyRepository companyRepository) throws UnauthorizedException, NoSuchAlgorithmException, CompanyInvalidException{
			String[] data = token.split(Pattern.quote("."));
			Optional<Company> optional = companyRepository.findById(data[0]);
			if(optional.isPresent()){
				String[] codeVector = login(optional.get()).split(Pattern.quote("."));
				if(data[1].equals(codeVector[1])) {
					return optional.get();
				}else {
					throw new UnauthorizedException();
				}
			}else{
				throw new CompanyInvalidException();
			}
			
	}


}
