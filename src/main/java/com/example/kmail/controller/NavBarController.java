package com.example.kmail.controller;

import com.example.kmail.domain.Email;
import com.example.kmail.domain.User;
import com.example.kmail.repository.UserRep;
import com.example.kmail.service.EmailService;
import com.example.kmail.service.UserService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;

@Controller
public class NavBarController {

    @Autowired
    UserService userService;
    @Autowired
    UserRep userRep;

    private EmailService emailService ;

    @PostMapping ("/newEmail")
    public String googleAccAuthorization (Model model){
        emailService = new EmailService();
        String url = emailService.getAuthorizationForm();
       model.addAttribute("url",url);
       return "rediToGoogleForm";

    }

    @GetMapping("/sosi")
    public String getGoogleAccInfo1 (@RequestParam String code, Model model,@AuthenticationPrincipal User user) throws ParseException {
        Email email = emailService.addUserEmail(code);
        userService.addEmail(user,email);
        return "mainForm";
    }


}
