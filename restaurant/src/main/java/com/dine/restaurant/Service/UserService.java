package com.dine.restaurant.Service;

import java.util.Optional;

import com.dine.restaurant.DocumentModel.User;
import com.dine.restaurant.DocumentModel.VerificationToken;
import com.dine.restaurant.Dto.UserDto;

public interface UserService {
	public void saveOrUpdateUser(User user);

	public User registerUser(UserDto userdto);

	public void saveVerificationToken(String token, User user);

	public String validateToken(String token);

	public VerificationToken generateNewToken(String oldToken);

	public User findUserByEmailAddress(String emailAddress);

	public void createTokenForPasswordReset(User user, String token);

	public String validatePasswordResetToken(String token);

	public Optional<User> getUserByPasswordResetToken(String token);

	public void changePassword(User user, String newPassword);

	public boolean checkIfValidOldPassword(User user, String oldPassword);

}
