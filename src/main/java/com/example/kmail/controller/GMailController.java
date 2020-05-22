package com.example.kmail.controller;

import com.example.kmail.domain.Email;
import com.example.kmail.domain.Message;
import com.example.kmail.repository.EmailRepo;
import com.example.kmail.service.GmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
@SessionAttributes({"messageList", "url"})
public class GMailController {

    @Autowired
    private GmailService gmailService;
    @Autowired
    private EmailRepo emailRepo;

    @ModelAttribute("messageList")
    public ArrayList<Message> createMessageList() {
        return new ArrayList<>();
    }

    @ModelAttribute("url")
    public String createUrl() {
        return "";
    }

    @GetMapping("/messagesToUser")
    public ModelAndView getToUserMessageList(Model model, @ModelAttribute("messageList") ArrayList<Message> messageList) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("url", "/messagesToUser");
        modelAndView.setViewName("sendMesForm");

        if (messageList.size() != 0 ) return modelAndView;

        try {
            Long amount = 12L;
            //if (messagesList != null) amount += messagesList.size();

            Email email = emailRepo.findByIsActive(true);
            messageList = gmailService.getMessagesToUser(email, amount);

            modelAndView.addObject(messageList);
            model.addAttribute("messageList", messageList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return modelAndView;
    }

    @PostMapping("/additionMessages")
    public ModelAndView getAdditionMessages(Model model,
                                            @ModelAttribute("messageList") ArrayList<Message> messageList,
                                            HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("url", "/messagesFromUser");
        modelAndView.setViewName("sendMesForm");

        String[] splitUrl = request.getHeader("referer").split("/");

        try {
            Email email = emailRepo.findByIsActive(true);

            switch (splitUrl[splitUrl.length-1]) {
                case "messagesFromUser": {
                    messageList = gmailService.getMessagesFromUser(email, messageList.size()+12L);
                    break;
                }
                case "messagesToUser": {
                    messageList = gmailService.getMessagesToUser(email, messageList.size()+12L);
                }
            }

            modelAndView.addObject(messageList);
            model.addAttribute("messageList", messageList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return modelAndView;
    }



    @GetMapping("/messagesFromUser")
    public ModelAndView getFromUserMessageList(Model model,
                                               @ModelAttribute("messageList") ArrayList<Message> messageList,
                                               HttpServletRequest request) {



        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("url", "/messagesFromUser");
        modelAndView.setViewName("sendMesForm");

        if (messageList.size() != 0 ) return modelAndView;

        try {
            Email email = emailRepo.findByIsActive(true);

            messageList = gmailService.getMessagesFromUser(email, 12L);

            modelAndView.addObject(messageList);
            model.addAttribute("messageList", messageList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return modelAndView;
    }

    @PostMapping("/sendMessage")
    public String sendMessage(Message message, Model model) {

        Email email = emailRepo.findByIsActive(true);

        model.addAttribute("isAlertSend", true);

        try {

            message.setFrom(email.getEmailName());
            gmailService.sendMessage(email, message);
            model.addAttribute("mesSend", true);

        } catch (Exception e) {
            model.addAttribute("mesNotSend", true);
        }
        model.addAttribute("alertType", true);

        return "sendMesForm";
    }

    @PostMapping("/trashMessage")
    public ModelAndView trashMessage(@RequestParam String mesId,
                                     HttpServletRequest request,
                                     Model model,
                                     @ModelAttribute("messageList") ArrayList<Message> messageList) {

        ModelAndView modelAndView = new ModelAndView("rediToGoogleForm");
        String[] splitUrl = request.getHeader("referer").split("/");
        model.addAttribute("url", splitUrl[splitUrl.length - 1]);

        Email email = emailRepo.findByIsActive(true);
        model.addAttribute("isAlertSend", true);


        try {
            gmailService.trashMessage(email.getEmailId(), mesId);
            ArrayList<Message> messages = gmailService.getMessagesFromUser(email, (long) messageList.size());

            modelAndView.addObject(messageList);
            model.addAttribute("messageList", messages);

            model.addAttribute("MesDelete", true);
        } catch (Exception e) {
            model.addAttribute("mesNotDelete", true);
        }

        return modelAndView;
    }

}
