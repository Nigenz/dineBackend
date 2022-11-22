package com.dine.restaurant.DocumentModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
@Data
@Document(collection = "users")
public class Users {
 
 @Id
 private String id;
 public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}

public Double getSalary() {
	return salary;
}

public void setSalary(Double salary) {
	this.salary = salary;
}

@NotBlank(message="Name cannot be blank")
@NotEmpty(message="Name cannot be empty")
@NotNull
private String name;
@NotBlank
@NotEmpty
@NotNull
 private String address;

@NotNull
@Min(1)
 private Double salary;
 
 public Users(String name, String address, Double salary) {
 this.name = name;
 this.address = address;
 this.salary = salary;
 }
}