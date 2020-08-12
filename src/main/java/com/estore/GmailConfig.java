package com.estore;

import java.io.InputStream;
import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;

@Configuration
public class GmailConfig {
	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setDefaultEncoding("UTF-8");
		sender.setHost("smtp.gmail.com");
		sender.setPort(587);
		sender.setUsername("tuanvuplbp@gmail.com");
		sender.setPassword("ttvtty2690");
		
		//thong số cấu hình
		Properties properties = sender.getJavaMailProperties();
		properties.setProperty("mail.transport.protocol","smtp");
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.starttls.enable","true");
		properties.setProperty("mail.debug","true");
				
		return sender;

	}
}