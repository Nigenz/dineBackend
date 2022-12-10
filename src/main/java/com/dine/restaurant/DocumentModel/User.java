package com.dine.restaurant.DocumentModel;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "users")
public class User {
	
	@Transient
	public static final String SEQUENCE_NAME = "user_sequence";

	@Id
	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String role;
	private boolean enable = false;
   
}