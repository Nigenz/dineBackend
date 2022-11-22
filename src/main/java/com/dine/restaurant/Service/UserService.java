package com.dine.restaurant.Service;

import com.dine.restaurant.DocumentModel.Users;

public interface UserService {
	public void addSampleData();
	public void listAll();
	public void findFirst();
	public void findByRegex();
	public void saveOrUpdateStudent(Users user);
}
