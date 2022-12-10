package com.dine.restaurant.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dine.restaurant.DocumentModel.PasswordResetToken;

@Repository
public interface PasswordResetTokenRepository extends MongoRepository<PasswordResetToken, Long>{

	PasswordResetToken findByToken(String token);

}
