package com.example.kmail.controller;

import com.example.kmail.domain.Email;
import com.example.kmail.domain.Message;
import com.example.kmail.domain.User;
import com.example.kmail.repository.EmailRepo;
import com.example.kmail.service.GmailService;
import com.example.kmail.service.MailService;
import com.google.api.services.gmail.Gmail;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

@Controller
@SessionAttributes({"messagesListTo","messagesListFrom", "url", "isUserOpenMessage, gmail"})
public class GMailController {

    @Autowired
    private GmailService gmailService;
    @Autowired
    private MailService mailService;
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
    @ModelAttribute("url")
    public String createUrl() {
        return "";
    }
    @ModelAttribute("isUserOpenMessage")
    public Boolean createIsUserOpenMessage() {
        return false;
    }
    @ModelAttribute("gmail")
    public Gmail createGmail() throws GeneralSecurityException, IOException {
        return null;
    }

    @PostMapping("greeting")
    public ModelAndView logInUserGmail (@AuthenticationPrincipal User user,
                                @ModelAttribute("gmail") Gmail gmail,
                                Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("rediToGoogleForm");

        Email email = emailRepo.findByIsActiveAndUserId(true,user.getId());

        if (email != null) {
            gmailService.connectToEmail(email);
            model.addAttribute("url", mailService.getAuthorizationForm());
            modelAndView.addObject("gmail",gmailService.getGmail());
        } else
            model.addAttribute("url", "greeting");

        return modelAndView;
    }

    @GetMapping("/sosi")
    public ModelAndView getGoogleAccInfo1(@RequestParam String code,
                                          @AuthenticationPrincipal User user,
                                          @ModelAttribute("gmail") Gmail gmail) throws ParseException {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("greeting");

        Email email = mailService.initAuthorize(code,user);
        mailService.addMail(user,email);
        gmailService.connectToEmail(email);

        modelAndView.addObject("gmail",gmail);

        return modelAndView;
    }

    @GetMapping("/messagesToUser")
    public ModelAndView getToUserMessageList(Model model,
                                             @ModelAttribute("messagesListTo") ArrayList<Message> messagesListTo,
                                             @ModelAttribute("isUserOpenMessage") Boolean isUserOpenMessage,
                                             @ModelAttribute("gmail") Gmail gmail,
                                             @AuthenticationPrincipal User user) {


        ModelAndView modelAndView = new ModelAndView();

        if (gmailService.getGmail() == null)
            if (gmail == null) {
                String url = mailService.getAuthorizationForm();
                model.addAttribute("url",url);
                modelAndView.setViewName("rediToGoogleForm");
                return modelAndView;
            } else gmailService.setGmail(gmail);

        modelAndView.setViewName("sendMesForm");
        model.addAttribute("url", "/messagesToUser");

        if (isUserOpenMessage) {
            modelAndView.addObject("isUserOpenMessage",false);
            model.addAttribute("messageList", messagesListTo);
            return modelAndView;
        }

        try {

            long amount ;
            if (messagesListTo.size() == 0) amount = 12L ;
            else amount = messagesListTo.size();

            Email email = emailRepo.findByIsActiveAndUserId(true,user.getId());
            messagesListTo = gmailService.getMessagesToUser(email, amount,messagesListTo);

            modelAndView.addObject("messagesListTo", messagesListTo);
            model.addAttribute("messageList", messagesListTo);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return modelAndView;
    }

    @GetMapping("/messagesFromUser")
    public ModelAndView getFromUserMessageList(Model model,
                                               @ModelAttribute("messagesListFrom") ArrayList<Message> messagesListFrom,
                                               @ModelAttribute("isUserOpenMessage")Boolean isUserOpenMessage,
                                               @ModelAttribute("gmail") Gmail gmail,
                                               @AuthenticationPrincipal User user) {

        ModelAndView modelAndView = new ModelAndView();

        if (gmailService.getGmail() == null)
            if (gmail == null) {
                String url = mailService.getAuthorizationForm();
                model.addAttribute("url",url);
                modelAndView.setViewName("rediToGoogleForm");
                return modelAndView;
            } else gmailService.setGmail(gmail);

        modelAndView.setViewName("sendMesForm");
        model.addAttribute("url", "/messagesFromUser");

        if (isUserOpenMessage) {
            modelAndView.addObject("isUserOpenMessage",false);
            model.addAttribute("messageList", messagesListFrom);
            return modelAndView;
        }

        try {
            long amount ;

            if (messagesListFrom.size() == 0) amount = 12L ;
            else amount = messagesListFrom.size();

            Email email = emailRepo.findByIsActiveAndUserId(true,user.getId());

            messagesListFrom = gmailService.getMessagesFromUser(email, amount,messagesListFrom);

            modelAndView.addObject("messagesListTo", messagesListFrom);
            model.addAttribute("messageList", messagesListFrom);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return modelAndView;
    }

    @PostMapping("/sendMessage")
    public ModelAndView sendMessage(Message message,
                              @AuthenticationPrincipal User user, @ModelAttribute("gmail") Gmail gmail, Model model) {

        ModelAndView modelAndView = new ModelAndView();

        if (gmailService.getGmail() == null)
            if (gmail == null) {
                String url = mailService.getAuthorizationForm();
                model.addAttribute("url",url);
                modelAndView.setViewName("rediToGoogleForm");
                return modelAndView;
            } else gmailService.setGmail(gmail);

        model.addAttribute("url", "/messagesFromUser");
        modelAndView.setViewName("rediToGoogleForm");

        Email email = emailRepo.findByIsActiveAndUserId(true,user.getId());

        model.addAttribute("isAlertSend", true);

        try {

            message.setFrom(email.getEmailName());
            gmailService.sendMessage(email, message);
            model.addAttribute("mesSend", true);

        } catch (Exception e) {
            model.addAttribute("mesNotSend", true);
        }
        model.addAttribute("alertType", true);

        return modelAndView;
    }

    @PostMapping("/trashMessage")
    public ModelAndView trashMessage(@RequestParam String mesId,
                                     @AuthenticationPrincipal User user,
                                     HttpServletRequest request,
                                     Model model,
                                     @ModelAttribute("messagesListTo") ArrayList<Message> messagesListTo,
                                     @ModelAttribute("messagesListFrom") ArrayList<Message> messagesListFrom,
                                     @ModelAttribute("gmail") Gmail gmail) {


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("rediToGoogleForm");

        if (gmailService.getGmail() == null)
            if (gmail == null) {
                String url = mailService.getAuthorizationForm();
                model.addAttribute("url",url);
                modelAndView.setViewName("rediToGoogleForm");
                return modelAndView;
            } else gmailService.setGmail(gmail);

        String[] splitUrl = request.getHeader("referer").split("/");
        model.addAttribute("url", splitUrl[splitUrl.length - 1]);

        Email email = emailRepo.findByIsActiveAndUserId(true,user.getId());
        model.addAttribute("isAlertSend", true);


        try {
            gmailService.trashMessage(email.getEmailId(), mesId);

            ArrayList<Message> messageList = new ArrayList<>();
            switch (splitUrl[splitUrl.length - 1]) {
                case "messagesToUser" : {
                    messageList = messagesListTo;
                    break;
                }
                case "messagesFromUser" : {
                    messageList = messagesListFrom;
                    break;
                }
            }

            ArrayList<Message> messages = gmailService.getMessagesFromUser(email, (long) messageList.size(),messageList);

            modelAndView.addObject(messageList);
            model.addAttribute("messageList", messages);

            model.addAttribute("MesDelete", true);
        } catch (Exception e) {
            model.addAttribute("mesNotDelete", true);
        }

        return modelAndView;
    }

    @GetMapping("/logOutMail")
    public String getProfile (@AuthenticationPrincipal User user, Model model) throws IOException {
        Email email = emailRepo.findByIsActiveAndUserId(true,user.getId());
        if (email != null) {
            HttpPost url = mailService.removeUserEmailAccess(email);
            HttpClient httpclient = HttpClients.createDefault();
            HttpResponse response = httpclient.execute(url);

            if (response.getStatusLine().getStatusCode() == 200) {
                emailRepo.delete(email);
                user.setActiveEmailName("none");
            }

        } else {
            user.setActiveEmailName("none");
        }
        model.addAttribute("url","/greeting");
        model.addAttribute("errorAAA","error");
        return "rediToGoogleForm" ;
    }



}
