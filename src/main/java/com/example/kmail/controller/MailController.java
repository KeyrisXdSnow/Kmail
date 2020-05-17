package com.example.kmail.controller;

import com.example.kmail.domain.Message;
import com.example.kmail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MailController {

    @Autowired
    private UserService userService;
    @Value("${spring.mail.username}")
    private String sender;

    @PostMapping("/sendMessage")
    public String sendMessage (@RequestParam String sendTo, @RequestParam String subject, @RequestParam String messageText, Model model) {
        Message message = new Message(sender,sendTo,subject,messageText,null);

        userService.sendMessage(message);
        return "mainForm";
    }
}
