package com.dine.restaurant.DocumentModel;

import java.util.Calendar;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "password_reset_token")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {

	private static final int EXPIRATION_TIME = 10;
	
	@Transient
	public static final String SEQUENCE_NAME = "password_reset_token";

	@Id
	private long id;
	private User user;
	private String token;
	private Date expirationTime;
	
	public PasswordResetToken(User user, String token) {
		super();
		this.user = user;
		this.token = token;
		this.expirationTime = calculateExpirationDateTime(EXPIRATION_TIME);
	}
	
	public PasswordResetToken(String token) {
		super();
		this.token = token;
		this.expirationTime = calculateExpirationDateTime(EXPIRATION_TIME);
	}

	private Date calculateExpirationDateTime(int expirationTime) {
		Calendar calender = Calendar.getInstance();
		calender.setTimeInMillis(new Date().getTime());
		calender.add(Calendar.MINUTE, expirationTime);
		return new Date(calender.getTime().getTime());
	}
	
	
}
