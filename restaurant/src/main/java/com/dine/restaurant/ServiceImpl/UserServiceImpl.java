package com.dine.restaurant.ServiceImpl;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dine.restaurant.DocumentModel.PasswordResetToken;
import com.dine.restaurant.DocumentModel.User;
import com.dine.restaurant.DocumentModel.VerificationToken;
import com.dine.restaurant.Dto.UserDto;
import com.dine.restaurant.Repository.PasswordResetTokenRepository;
import com.dine.restaurant.Repository.UserRepository;
import com.dine.restaurant.Repository.VerificationTokenRepository;
import com.dine.restaurant.Service.SequenceGeneratorService;
import com.dine.restaurant.Service.UserService;


@Service
public class UserServiceImpl implements UserService {
		@Autowired
		private UserRepository userRepository;
		
		@Autowired
		private PasswordEncoder passwordEncoder;
		
		@Autowired
		private VerificationTokenRepository verificationTokenRepository;
		
		@Autowired
		private PasswordResetTokenRepository passwordResetTokenRepository;
		
		@Autowired
		private SequenceGeneratorService sequenceGeneratorService;
		

		
		 public void saveOrUpdateUser(User user) {
			 System.out.println("Hereee");
			 userRepository.save(user);
		 }

		@Override
		public User registerUser(UserDto userdto) {
			User user = new User();
	    	user.setId(sequenceGeneratorService.getSequenceNumber(user.SEQUENCE_NAME));
			user.setFirstName(userdto.getFirstName());
			user.setLastName(userdto.getLastName());
			user.setEmail(userdto.getEmail());
			user.setPassword(passwordEncoder.encode(userdto.getPassword()));
			user.setRole("USER");
			userRepository.save(user);
			return user;
		}

		@Override
		public void saveVerificationToken(String token, User user) {
          VerificationToken verificationTkn = new VerificationToken(user, token);
		  verificationTkn.setId(sequenceGeneratorService.getSequenceNumber(verificationTkn.SEQUENCE_NAME));
          verificationTokenRepository.save(verificationTkn);
		}

		@Override
		public String validateToken(String token) {
			VerificationToken verificationTkn = verificationTokenRepository.findByToken(token);
			
			if (verificationTkn == null) {
				return "false";
			}
			User user = verificationTkn.getUser();
			Calendar cal = Calendar.getInstance();
			
			if ((verificationTkn.getExpirationTime().getTime() - cal.getTime().getTime()) <= 0) {
				System.out.println("ERROR");
				verificationTokenRepository.delete(verificationTkn);
				return "tokenExpired";
			}
			
			user.setEnable(true);
			userRepository.save(user);
			
			return "true";
		}

		@Override
		public VerificationToken generateNewToken(String oldToken) {
			VerificationToken verificationTkn = verificationTokenRepository.findByToken(oldToken);
			verificationTkn.setToken(UUID.randomUUID().toString());
			verificationTokenRepository.save(verificationTkn);
			return verificationTkn;
		}

		@Override
		public User findUserByEmailAddress(String emailAddress) {
			return userRepository.findUserByEmail(emailAddress);
		}

		@Override
		public void createTokenForPasswordReset(User user, String token) {
			PasswordResetToken passwordResetToken = new PasswordResetToken(user, token);
			passwordResetToken.setId(sequenceGeneratorService.getSequenceNumber(passwordResetToken.SEQUENCE_NAME));
			passwordResetTokenRepository.save(passwordResetToken);
		}

		@Override
		public String validatePasswordResetToken(String token) {
			PasswordResetToken passwordResetTkn = passwordResetTokenRepository.findByToken(token);
			
			if (passwordResetTkn == null) {
				return "false";
			} 
			User user = passwordResetTkn.getUser();
			Calendar cal = Calendar.getInstance();
			
			if ((passwordResetTkn.getExpirationTime().getTime() - cal.getTime().getTime()) <= 0) {
				System.out.println("ERROR");
				passwordResetTokenRepository.delete(passwordResetTkn);
				return "tokenExpired";
			}
			
//			user.setEnable(true);
//			userRepository.save(user);
//			
			return "true";
		}

		@Override
		public Optional<User> getUserByPasswordResetToken(String token) {
			return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
		}

		@Override
		public void changePassword(User user, String newPassword) {
			user.setPassword(passwordEncoder.encode(newPassword));
			userRepository.save(user);
		}

		@Override
		public boolean checkIfValidOldPassword(User user, String oldPassword) {
			// TODO Auto-generated method stub
			return passwordEncoder.matches(oldPassword, user.getPassword());
		}

}
