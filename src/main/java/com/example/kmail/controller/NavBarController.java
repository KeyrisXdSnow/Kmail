package com.example.kmail.controller;

import com.example.kmail.domain.Email;
import com.example.kmail.domain.User;
import com.example.kmail.repository.EmailRepo;
import com.example.kmail.repository.UserRep;
import com.example.kmail.service.EmailService;
import com.example.kmail.service.GmailService;
import com.example.kmail.service.UserService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Controller
public class NavBarController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRep userRep;
    @Autowired
    private EmailRepo emailRepo;
    @Autowired
    private EmailService emailService ;
    @Autowired
    private GmailService gmailService;


    @PostMapping ("/newEmail")
    public String googleAccAuthorization (Model model){
        String url = emailService.getAuthorizationForm();
       model.addAttribute("url",url);
       return "rediToGoogleForm";

    }

    @GetMapping("/sosi")
    public String getGoogleAccInfo1 (@RequestParam String code,@AuthenticationPrincipal User user, Model model) throws ParseException, GeneralSecurityException, IOException {
        Email email = emailService.addUserEmail(code);
        userService.addEmail(user,email);
        gmailService.connectToEmail(email);
        return "mainForm";
    }


    @GetMapping("/getProfile")
    public String getProfile (@AuthenticationPrincipal User user, Model model){
        Email email = emailRepo.findByIsActive(true);
        if (email != null) {
            String url = emailService.removeUserEmailAccess(email);
            System.out.println(url);
            model.addAttribute("url", url);
        } else model.addAttribute("url","/mainForm");

        return "rediToGoogleForm" ;
    }




}
