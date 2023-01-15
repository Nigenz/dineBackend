package com.dine.restaurant.Events.Listener;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.dine.restaurant.DocumentModel.User;
import com.dine.restaurant.Events.UserEnableEvent;
import com.dine.restaurant.Service.UserService;

@Component
public class UserEnableEventListener implements ApplicationListener<UserEnableEvent>{
    @Autowired
    private UserService userService;
	@Override
	public void onApplicationEvent(UserEnableEvent event) {
		// create verification token
		User user = event.getUser();
		String token = UUID.randomUUID().toString();
		userService.saveVerificationToken(token, user);
		
		//send mail
		
		String url = event.getApplicationUrl() + "/verifyRegistration?token=" + token;
		
		System.out.println("Click to verify = "+url);
		
	}

}
