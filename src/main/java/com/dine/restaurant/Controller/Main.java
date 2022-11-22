package com.dine.restaurant.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.dine.restaurant.Service.UserService;

@Controller
public class Main {
	@Autowired
	private UserService userservice;
	
    @GetMapping(value="/login",produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> logpage() {
    	System.out.println("ffff");
    	HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.OK);

    }
    
    @GetMapping(value="/add",produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> addData() {
    	userservice.addSampleData();
    	HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.OK);

    }
}