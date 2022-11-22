package com.dine.restaurant.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dine.restaurant.DocumentModel.Users;
import com.dine.restaurant.Repository.UserRepository;
import com.dine.restaurant.Service.UserService;


@Service
public class UserServiceImpl implements UserService {
		@Autowired
		private UserRepository repository;
	
	     public void deleteAll() {
		 System.out.println("Deleting all records..");
		 repository.deleteAll();
		 }
		 
		 public void addSampleData() {
		 System.out.println("Adding sample data");
		 repository.save(new Users("Jack Bauer", "New York", 11111d));
		 repository.save(new Users("Harvey Spectre", "London", 22222d));
		 repository.save(new Users("Mike Ross", "New Jersey", 333333d));
		 repository.save(new Users("Louise Litt", "Kathmandu", 44444d));
		 }
		 
		 public void listAll() {
		 System.out.println("Listing sample data");
		 repository.findAll().forEach(u -> System.out.println(u));
		 }
		 
		 public void findFirst() {
		 System.out.println("Finding first by Name");
		 Users u = repository.findFirstByName("Louise Litt");
		 System.out.println(u);
		 }
		 
		 public void findByRegex() {
		 System.out.println("Finding by Regex - All with address starting with ^New");
		 repository.findCustomByRegExAddress("^New").forEach(u -> System.out.println(u));
		 }
		
}
