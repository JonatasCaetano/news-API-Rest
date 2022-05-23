package com.news.api.services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.news.api.models.entities.Company;
import com.news.api.models.entities.User;
import com.news.api.repositories.CompanyRepository;
import com.news.api.repositories.UserRepository;

@Service
public class Authorization {
	
	public static String login(User user) {
		String code = new String(user.getEmail() + user.getPassword() + LocaDateTimeFormatter.formatDate(user.getLastLogin()) + "bananasDePijamas");
		//System.out.println(code);
		 try {
		        final byte[] hash = MessageDigest.getInstance("SHA-256").digest(code.getBytes(StandardCharsets.UTF_8));
		        final StringBuilder hashStr = new StringBuilder(hash.length);

		        for (byte hashByte : hash)
		            hashStr.append(Integer.toHexString(255 & hashByte));

		        return new String(user.getId() + "." + hashStr.toString());
		    } catch (NoSuchAlgorithmException e) {
		        e.printStackTrace();
		        return null;
		    }
	}

	public static String login(Company company) {
		String code = new String(company.getEmail() + company.getPassword() + LocaDateTimeFormatter.formatDate(company.getLastLogin()) + "bananasDePijamas");
		//System.out.println(code);
		 try {
		        final byte[] hash = MessageDigest.getInstance("SHA-256").digest(code.getBytes(StandardCharsets.UTF_8));
		        final StringBuilder hashStr = new StringBuilder(hash.length);

		        for (byte hashByte : hash)
		            hashStr.append(Integer.toHexString(255 & hashByte));

		        return new String(company.getId() + "." + hashStr.toString());
		    } catch (NoSuchAlgorithmException e) {
		        e.printStackTrace();
		        return null;
		    }
	}
	
	public static User isAuthorization(String token, UserRepository userRepository) throws Exception {
		 try {
		       String[] data = token.split(Pattern.quote("."));
		       //String a = data[0];
		       //System.out.println(a);
		       String b = data[1];
		       //System.out.println(b);
		       User user = userRepository.findById(data[0]).get();
		       String code = login(user);
		       //System.out.println(code);
		       String[] codeVector = code.split(Pattern.quote("."));
		       //String c = codeVector[0];
		       //System.out.println(c);
		       String d = codeVector[1];
		       //System.out.println(d);
		       if(b.equals(d)) {
		    	   return user;
		       }else {
		    	   throw new Exception();
		       }
		    } catch (Exception e) {
		    	throw e;
		    }
	}

	public static Company isAuthorization(String token, CompanyRepository companyRepository) throws Exception {
		try {
			  String[] data = token.split(Pattern.quote("."));
			  //String a = data[0];
			  //System.out.println(a);
			  String b = data[1];
			  //System.out.println(b);
			  Company company = companyRepository.findById(data[0]).get();
			  String code = login(company);
			  //System.out.println(code);
			  String[] codeVector = code.split(Pattern.quote("."));
			  //String c = codeVector[0];
			  //System.out.println(c);
			  String d = codeVector[1];
			  //System.out.println(d);
			  if(b.equals(d)) {
				  return company;
			  }else {
				  throw new Exception();
			  }
		   } catch (Exception e) {
			   throw e;
		   }
   }
}
