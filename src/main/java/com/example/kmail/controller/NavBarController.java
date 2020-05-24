package com.example.kmail.controller;

import com.example.kmail.repository.EmailRepo;
import com.example.kmail.repository.UserRepo;
import com.example.kmail.service.GmailService;
import com.example.kmail.service.MailService;
import com.example.kmail.service.UserService;
import com.google.api.services.gmail.Gmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NavBarController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private EmailRepo emailRepo;
    @Autowired
    private MailService mailService;
    @Autowired
    private GmailService gmailService;

    @ModelAttribute("gmail")
    public Gmail createGmail() {
        return new Gmail(null,null,null);
    }

    @PostMapping ("/newEmail")
    public String googleAccAuthorization (Model model){
       String url = mailService.getAuthorizationForm();
       model.addAttribute("url",url);
       return "rediToGoogleForm";

    }








}
