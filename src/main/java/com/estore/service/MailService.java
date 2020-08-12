package com.estore.service;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.estore.bean.MailInfo;

@Service
public class MailService {
	@Autowired
	JavaMailSender mailSender;

	// KHAI CÁC THÔNG TIN TRONG CLASS BEAN MailInfo
	public void send(MailInfo email) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
		helper.setFrom(email.getFrom());
		helper.setTo(email.getTo());
		helper.setSubject(email.getSubject());
		helper.setText(email.getBody(), true); // true = unicode
		helper.setReplyTo(email.getFrom()); //
		
		if(email.getCc() != null) {
			helper.setCc(email.getCc()); // mọt chuỗi
		}
		if(email.getBcc() != null) {
			helper.setBcc(email.getBcc());
		}
		if(email.getFiles() != null) {
			String[] paths = email.getFiles().split(";");  //  các đường dẫn đã tách
			for(String path : paths) {
				File file = new File(path);
				helper.addAttachment(file.getName(), file);
			}
		}
		mailSender.send(message);

	}

}
