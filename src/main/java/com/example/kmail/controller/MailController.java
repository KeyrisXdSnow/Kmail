package com.example.kmail.controller;

import com.example.kmail.domain.Message;
import com.example.kmail.repository.UserRep;
import com.example.kmail.service.EmailService;
import com.example.kmail.service.MailSender;
import com.example.kmail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MailController {

    @Autowired
    private UserService userService;
    @Autowired
    private MailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;
    @Autowired
    UserRep userRepo;

    @PostMapping("/sendMessage")
    public String sendMessage (@RequestParam String sendTo, @RequestParam String subject, @RequestParam String messageText, Model model) {
        mailSender.connectToSMTP(sender);
        Message message = new Message(sender,sendTo,subject,messageText,null);
        userService.sendMessage(message);
        return "mainForm";
    }

    @GetMapping("/sendMessage")
    public String sendMessageList (Model model) {
        model.addAttribute("messageList",model.addAttribute("users",userRepo.findAll()));
        return "sendMesForm";
    }

    @PostMapping ("/newEmail")
    public String helllo (Model model) {

        return  "mainForm";
    }
}
