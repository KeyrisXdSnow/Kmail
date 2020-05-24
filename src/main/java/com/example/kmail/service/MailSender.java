package com.example.kmail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class MailSender {

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Value("spring.mail.spring.mail.username")
    private String username ;

    /**
     * send an email with the authorization code code when the user is registered in the service
     * @param mailTo - registered user mail
     * @param subject - message subject
     * @param message - message body
     */
    public void send(String mailTo, String subject, String message) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(mailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);

    }

}
