package com.example.kmail.controller;


import com.example.kmail.domain.Email;
import com.example.kmail.domain.Message;
import com.example.kmail.repository.EmailRepo;
import com.example.kmail.service.GmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
@RequestMapping({"/messagesToUser", "/messagesFromUser"})
@SessionAttributes({"messageList", "url"})
public class MessageController {

    @Autowired
    private GmailService gmailService;
    @Autowired
    private EmailRepo emailRepo;

    @GetMapping("{mesId}")
    public String userEditForm(@PathVariable String mesId,
                               @ModelAttribute("messageList") ArrayList<Message> messageList,
                               HttpServletRequest request,
                               Model model) {
        try {
            Email email = emailRepo.findByIsActive(true);
            Message message  = gmailService.getFullMessage(email,mesId);
            model.addAttribute("message",message);

        } catch (Exception e) {
            return "mainForm";
        }

        return "message";

    }

}
