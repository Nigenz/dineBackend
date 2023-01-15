package com.dine.restaurant.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dine.restaurant.DocumentModel.User;

@Repository
public interface UserRepository extends MongoRepository<User,Long> {

	  User findUserByEmail(String emailAddress);
 
}
