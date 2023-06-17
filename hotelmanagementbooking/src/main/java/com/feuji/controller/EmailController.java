package com.feuji.controller;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.dto.EmailDetails;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class EmailController {
	@Autowired
	private JavaMailSender mailSender;

	@GetMapping("/msg")
	public String msg() {
		return "hello";
	}



	
	@PostMapping("/sendemailwithattachment")
	public String sendEmailWithAttachment(@RequestBody EmailDetails emailDetails) throws MessagingException {
		log.info("send mail controller");

		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

		mimeMessageHelper.setFrom("hotelamsr@gmail.com");
		mimeMessageHelper.setTo(emailDetails.getToEmail());
		mimeMessageHelper.setText(emailDetails.getBody());
		mimeMessageHelper.setSubject(emailDetails.getSubject());

		FileSystemResource fileSystem = new FileSystemResource(new File(emailDetails.getAttachment()));

		if (fileSystem.getFilename() != null) {
			mimeMessageHelper.addAttachment(fileSystem.getFilename(), fileSystem);
		}

		mailSender.send(mimeMessage);
		return "mail sent successfully";
	}

}
