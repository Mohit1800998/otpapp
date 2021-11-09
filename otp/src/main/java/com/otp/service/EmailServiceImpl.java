package com.otp.service;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.otp.dao.EmailRepository;
import com.otp.entities.Check;
import com.otp.entities.Email;
import com.otp.entities.Validate;
import com.otp.sender.EmailSenderService;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private EmailRepository repo;
	
	@Autowired
    private EmailSenderService services;
	
	@Override
	public List<Email> getUsers() {
		// TODO Auto-generated method stub
		return (List<Email>) repo.findAll();
	}

	@Override
	public void addUser(Email email) {
		// TODO Auto-generated method stub
		repo.save(email);
		email.setStartDate(LocalTime.now());
		email.setEndDate(email.getStartDate().plusMinutes(5));
		int randomPin   =(int) (Math.random()*9000)+1000;
        String otp1  = String.valueOf(randomPin);
		int otp = Integer.parseInt(otp1);
		email.setOtp(otp);
		repo.save(email);
//		sendSimpleMail(email);
	}

	Check check = new Check();
	
	@Override
	public void validateUser(Validate validate) {
		// TODO Auto-generated method stub
		
		String mail = null;
    	int otp = 0;
    	LocalTime start = null;
    	LocalTime end = null;
    	
    	List<Email> l = repo.findAll();
    	for(int i = 0;i<l.size();i++) {
    		Email h = l.get(i);
    		if(h.getEmail().equals(validate.getEmail())) {
    			mail = h.getEmail();
    			otp = h.getOtp();
    			start = h.getStartDate();
    			end = h.getEndDate();
    			
    		}
    	}
    	
    	LocalTime now = LocalTime.now();
    	if(validate.getEmail().equals(mail) && Integer.parseInt(validate.getOtp()) == otp && now.isBefore(end) && now.isAfter(start)) {
    		System.out.println("True");
    		check.setCheck("True");
    		services.sendSuccessMail(validate.getEmail(), "OTP Is Verified", "This is a system generated success mail.");
        	
    	}
    	else {
    		check.setCheck("False");
    		System.out.println("False");
    	}
		
	}
	
//	@Autowired
//	private JavaMailSender mailSender;
//	
//	public void sendSimpleMail(Email email) {
//		
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setFrom("yogeshyadav80666@gmail.com");
//		message.setTo(email.getEmail());
//		message.setText("This is the System generated OTP");
//		message.setSubject("Get Your OTP :: "+email.getOtp());
//		
//		mailSender.send(message);
//	}

}
