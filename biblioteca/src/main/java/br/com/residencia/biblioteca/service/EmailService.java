package br.com.residencia.biblioteca.service;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	@Autowired
	public JavaMailSender emailSender;
	
	@Value("${mail.from}")
	private String mailFrom;
	
	@Value("${spring.mail.host}")
	private String mailHost;
	
	@Value("${spring.mail.port}")
	private Integer mailPort;
	
	@Value("${spring.mail.username}")
	private String username;
	
	@Value("${spring.mail.password}")
	private String password;

	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		Properties prop = new Properties();
		
		mailSender.setHost(mailHost);
		mailSender.setPort(mailPort);
		mailSender.setUsername(username);	
		mailSender.setPassword(password);
		
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", true);
		mailSender.setJavaMailProperties(prop);
		return mailSender;
	}
	
	public EmailService(JavaMailSender javaMailSender) {
		this.emailSender = javaMailSender;
	}
	
	public void sendMail(String destinatario, String assunto, String mensagem) {
		var mailMessage = new SimpleMailMessage();
		mailMessage.setTo(destinatario);
		mailMessage.setSubject(assunto);
		mailMessage.setText(mensagem);
		mailMessage.setFrom(mailFrom);
		try {
			emailSender.send(mailMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
