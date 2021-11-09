package com.otp.service;

import java.util.List;

import com.otp.entities.Email;
import com.otp.entities.Validate;

public interface EmailService {
	
	public List<Email> getUsers();
	public void addUser( Email email);
	public void validateUser(Validate validate);

}
