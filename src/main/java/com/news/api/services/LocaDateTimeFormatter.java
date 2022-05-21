package com.news.api.services;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

@Service
public class LocaDateTimeFormatter {
	
	public static String formatDate(LocalDateTime localDateTime){
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		 return formatter.format(localDateTime);
	}
	
	public static LocalDateTime getLocalDateTime(String date) {
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		 return LocalDateTime.parse(date, formatter);
	}
}
