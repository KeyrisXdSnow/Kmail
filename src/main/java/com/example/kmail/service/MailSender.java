package com.example.kmail.service;

import com.example.kmail.domain.SmtpConfig;
import com.example.kmail.repository.SmtpConfigRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;

@Service
public class MailSender {

    private JavaMailSenderImpl mailSender;
    @Autowired
    private SmtpConfigRepo smtpConfigRepo;

    @Value("spring.mail.spring.mail.username")
    private String username ;

    public void send(String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);

    }

    public void connectToSMTP (String sender) {
        String s = sender.substring(sender.indexOf('@')+1,sender.lastIndexOf('.'));
        SmtpConfig smtpConfigs =  smtpConfigRepo.findByPostServiceDomain(s);

        if (smtpConfigs != null) {
            mailSender = new JavaMailSenderImpl();

            mailSender.setHost(smtpConfigs.getHost());
            mailSender.setPort(smtpConfigs.getPort());
            mailSender.setUsername(username);
            mailSender.setPassword("4xg9v543");

            Properties properties = mailSender.getJavaMailProperties();
            properties.setProperty("mail.transport.protocol", smtpConfigs.getProtocol());
            properties.setProperty("mail.smtp.auth", String.valueOf(smtpConfigs.isAuth()));
            properties.setProperty("mail.smtp.starttls.enable", String.valueOf(smtpConfigs.isEnable()));
            properties.setProperty("mail.debug", String.valueOf(smtpConfigs.isDebug()));
        }
    }


    @Bean
    public JavaMailSender getMailSender () {
        return mailSender;
    }



}
