package com.dine.restaurant.Events;

import org.springframework.context.ApplicationEvent;

import com.dine.restaurant.DocumentModel.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEnableEvent extends ApplicationEvent{

	private User user;
	private String applicationUrl;
	
	public UserEnableEvent(User user, String applicationUrl) {
		super(user);
		this.user = user;
		this.applicationUrl = applicationUrl; 
	}

}
