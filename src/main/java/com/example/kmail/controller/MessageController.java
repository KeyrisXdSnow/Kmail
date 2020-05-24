package com.example.kmail.controller;

import com.example.kmail.domain.Email;
import com.example.kmail.domain.Message;
import com.example.kmail.domain.User;
import com.example.kmail.repository.EmailRepo;
import com.example.kmail.service.GmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
@RequestMapping({"/messagesToUser", "/messagesFromUser"})
@SessionAttributes({"messagesListTo", "messagesListFrom", "url", "isUserOpenMessage"})
public class MessageController {

    @Autowired
    private GmailService gmailService;
    @Autowired
    private EmailRepo emailRepo;

    @ModelAttribute("messagesListTo")
    public ArrayList<Message> createMessagesListTo() {
        return new ArrayList<>();
    }

    @ModelAttribute("messagesListFrom")
    public ArrayList<Message> createMessagesListFrom() {
        return new ArrayList<>();
    }

    @ModelAttribute("isUserOpenMessage")
    public Boolean createIsUserOpenMessage() {
        return false;
    }

    @GetMapping("{mesId}")
    public ModelAndView userEditForm(@ModelAttribute("isUserOpenMessage") Boolean isUserOpenMessage,
                                     @PathVariable String mesId,
                                     @AuthenticationPrincipal User user,
                                     @ModelAttribute("messagesListTo") ArrayList<Message> messagesListTo,
                                     @ModelAttribute("messagesListFrom") ArrayList<Message> messagesListFrom,
                                     Model model) {

            ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("isUserOpenMessage", true);

        try {
            Email email = emailRepo.findByIsActiveAndUserId(true, user.getId());
            Message message = gmailService.getFullMessage(email, mesId);
            model.addAttribute("message", message);
            modelAndView.setViewName("message");

            if (message.getLabels() != null) {
                if (message.getLabels().contains("INBOX")) {
                    for (Message mes : messagesListTo)
                        if (mes.getMesId().equals(mesId)) {
                            mes.setLabels(message.getLabels());
                            modelAndView.addObject("messagesListTo", messagesListTo);
                            break;
                        }
                }
                if (message.getLabels().contains("SEND")) {
                    for (Message mes : messagesListFrom)
                        if (mes.getMesId().equals(mesId)) {
                            mes.setLabels(message.getLabels());
                            modelAndView.addObject("messagesListFrom", messagesListFrom);
                            break;
                        }
                }
            }

        } catch (Exception e) {
            model.addAttribute("errorAAA","error");
            modelAndView.setViewName("greeting");
        }

        return modelAndView;

    }

    @PostMapping("/additionMessages")
    public ModelAndView getAdditionMessages(Model model,
                                            @ModelAttribute("messagesListTo") ArrayList<Message> messagesListTo,
                                            @ModelAttribute("messagesListFrom") ArrayList<Message> messagesListFrom,
                                            HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("rediToGoogleForm");

        String[] splitUrl = request.getHeader("referer").split("/");
        String type;
        if (splitUrl[splitUrl.length - 1].equals("messagesToUser") ^ splitUrl[splitUrl.length - 1].equals("messagesFromUser"))
            type = splitUrl[splitUrl.length - 1];
        else type = splitUrl[splitUrl.length - 2];

        try {
            switch (type) {
                case "messagesFromUser": {
                    for (int i = 0; i < messagesListFrom.size() + 12L; i++)
                        messagesListFrom.add(new Message());

                    modelAndView.addObject("messagesListFrom", messagesListFrom);
                    model.addAttribute("url", "/messagesFromUser");

                    break;
                }
                case "messagesToUser": {
                    for (int i = 0; i < messagesListFrom.size() + 12L; i++)
                        messagesListTo.add(new Message());

                    modelAndView.addObject("messagesListTo", messagesListTo);
                    model.addAttribute("url", "/messagesToUser");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return modelAndView;
    }

}
