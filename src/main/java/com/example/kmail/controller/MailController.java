package com.example.kmail.controller;

import org.springframework.stereotype.Controller;

@Controller
public class MailController {

//    @Autowired
//    private UserService userService;
//    @Autowired
//    private MailSender mailSender;
//
//    @Value("${spring.mail.username}")
//    private String sender;
//    @Autowired
//    UserRep userRepo;
//
//    @PostMapping("/sendMessage")
//    public String sendMessage (@RequestParam String sendTo, @RequestParam String subject, @RequestParam String messageText, Model model) {
//        mailSender.connectToSMTP(sender);
//        Message message = new Message(sender,sendTo,subject,messageText,null);
//        userService.sendMessage(message);
//        return "mainForm";
//    }
//
//    @GetMapping("/sendMessage")
//    public String sendMessageList (Model model) {
//        model.addAttribute("messageList",model.addAttribute("users",userRepo.findAll()));
//        return "sendMesForm";
//    }


}
