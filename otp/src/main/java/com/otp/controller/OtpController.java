package com.otp.controller;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.otp.dao.EmailRepository;
import com.otp.entities.Check;
import com.otp.entities.Email;
import com.otp.entities.Validate;
import com.otp.sender.EmailSenderService;
import com.otp.service.EmailService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class OtpController {

	@Autowired
    private EmailSenderService services;
	
	@Autowired
	private EmailService service;

	@Autowired
	private EmailRepository repo;
	
	Check check = new Check();
	
    @GetMapping("/emails")
    public Check getUsers() {
        return check;
    }

    @PostMapping("/emails")
    public void addUser(@RequestBody Email email) {
        this.service.addUser(email);
        check.setCheck("False");
     	services.sendSimpleMail(email.getEmail(),"Get Your OTP :: "+ email.getOtp() + " ","This is the system generated OTP");
    	   
    }
    
    @PostMapping("/validate")
    public void validateUser(@RequestBody Validate validate) {
    	
    	this.service.validateUser(validate);
    }

}
