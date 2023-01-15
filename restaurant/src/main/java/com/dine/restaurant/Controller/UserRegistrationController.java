package com.dine.restaurant.Controller;

import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.dine.restaurant.DocumentModel.User;
import com.dine.restaurant.DocumentModel.VerificationToken;
import com.dine.restaurant.Dto.PasswordResetDto;
import com.dine.restaurant.Dto.UserDto;
import com.dine.restaurant.Events.UserEnableEvent;
import com.dine.restaurant.Service.UserService;
@Controller
public class UserRegistrationController {

	@Autowired
	private UserService userservice;
	
	@Autowired
	private ApplicationEventPublisher publisher;
		

	@PostMapping(value = "/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userdto, final HttpServletRequest request) {
    	System.out.println("in controller");
    	User user = userservice.registerUser(userdto);
    	
    	publisher.publishEvent(new UserEnableEvent(
    			user,
    			applicationUrlContext(request)
    	));
        return new ResponseEntity<String>("User added successfully", HttpStatus.OK);
    }
	
	@GetMapping(value = "/verifyRegistration")
    public ResponseEntity<String> verifyRegistration(@RequestParam("token") String token) {
		
    	String verifyResult = userservice.validateToken(token);
    	if(verifyResult.equalsIgnoreCase("true")) {
            return new ResponseEntity<String>("User verified successfully", HttpStatus.OK);
    	}
        return new ResponseEntity<String>("Unauthorised User", HttpStatus.OK);
    }
	
	@GetMapping(value = "/resendVerificationToken")
    public ResponseEntity<String> resendVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest request) {
    	VerificationToken verificationTkn = userservice.generateNewToken(oldToken);
    	User user = verificationTkn.getUser();
    	resendVerificationToken(user, applicationUrlContext(request), verificationTkn);
        return new ResponseEntity<String>("Vefication link sent again", HttpStatus.OK);
    }
	
	@PostMapping(value = "/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetDto passwordResetDto, HttpServletRequest request) {
		User user = userservice.findUserByEmailAddress(passwordResetDto.getEmailAddress());
		String url = "";
		if(user!= null) {
			String token =  UUID.randomUUID().toString();
			userservice.createTokenForPasswordReset(user, token);
			url = passwordResetToken(user,applicationUrlContext(request),token);
		}
    	
        return new ResponseEntity<String>(url, HttpStatus.OK);
    }
	
	@PostMapping(value = "/savePassword")
    public ResponseEntity<String> savePassword(@RequestParam("token") String token, @RequestBody PasswordResetDto passwordResetDto) {
		
		String verifyPassResult = userservice.validatePasswordResetToken(token);
    	if(!verifyPassResult.equalsIgnoreCase("true")) {
            return new ResponseEntity<String>("Invalid User Token", HttpStatus.OK);
    	} 
    	Optional<User> user = userservice.getUserByPasswordResetToken(token);
    	if(user.isPresent()) {
    		userservice.changePassword(user.get(),passwordResetDto.getNewPassword());
            return new ResponseEntity<String>("Password reset successfully", HttpStatus.OK);
    	}
    	else {
            return new ResponseEntity<String>("Invalid Token", HttpStatus.OK);
    	}
    }
	
	@PostMapping(value = "/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody PasswordResetDto passwordResetDto) {
		
		User user = userservice.findUserByEmailAddress(passwordResetDto.getEmailAddress());
    	if(!userservice.checkIfValidOldPassword(user, passwordResetDto.getOldPassword())) {
            return new ResponseEntity<String>("password not matches", HttpStatus.OK);
    	}
    	userservice.changePassword(user, passwordResetDto.getNewPassword());
        return new ResponseEntity<String>("Password changed successfully", HttpStatus.OK);
    }


	private String passwordResetToken(User user, String applicationUrlContext, String token) {
		String url = applicationUrlContext + "/savePassword?token=" + token;
		System.out.println("Click to verify = "+url);
		return url;
	}

	private void resendVerificationToken(User user, String applicationUrlContext, VerificationToken verificationTkn) {
		String url = applicationUrlContext + "/verifyRegistration?token=" + verificationTkn.getToken();
		System.out.println("Click to verify = "+url);
	}

	private String applicationUrlContext(HttpServletRequest request) {
		
		return "http://" + 
				request.getServerName() +
				":" +
				request.getServerPort() +
				request.getContextPath();
	}
}
