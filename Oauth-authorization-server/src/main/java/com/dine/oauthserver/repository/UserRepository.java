package com.dine.oauthserver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dine.oauthserver.document.User;


@Repository
public interface UserRepository extends MongoRepository<User,Long> {

	  User findUserByEmail(String emailAddress);
 
}
