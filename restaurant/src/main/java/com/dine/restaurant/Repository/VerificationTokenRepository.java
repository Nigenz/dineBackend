package com.dine.restaurant.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dine.restaurant.DocumentModel.VerificationToken;

@Repository
public interface VerificationTokenRepository extends MongoRepository<VerificationToken, Long>{

	VerificationToken findByToken(String token);

}
