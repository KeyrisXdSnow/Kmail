package com.example.kmail.controller;

import com.example.kmail.domain.Email;
import com.example.kmail.domain.Message;
import com.example.kmail.domain.User;
import com.example.kmail.repository.EmailRepo;
import com.example.kmail.repository.UserRepo;
import com.example.kmail.service.GmailService;
import com.example.kmail.service.MailService;
import com.google.api.services.gmail.Gmail;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.UUID;

@Controller
@SessionAttributes({"messagesListTo","messagesListFrom", "url", "isUserOpenMessage, gmail"})
public class GMailController {

    @Autowired
    private GmailService gmailService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MailService mailService;
    @Autowired
    private EmailRepo emailRepo;
    @Value("${upload.path}")
    private String uploadPath;

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

    @PostMapping("/greeting")
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


    @PostMapping ("/newEmail")
    public String googleAccAuthorization (Model model){
        String url = mailService.getAuthorizationForm();
        model.addAttribute("url",url);
        return "rediToGoogleForm";

    }




    @GetMapping("/sosi")
    public ModelAndView getGoogleAccInfo1(@RequestParam String code,
                                          @AuthenticationPrincipal User user,
                                          @ModelAttribute("gmail") Gmail gmail) throws ParseException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("greeting");
        try {
            Email email = mailService.initAuthorize(code, user);
            mailService.addMail(user, email);
            gmailService.connectToEmail(email);
            modelAndView.addObject("gmail", gmail);
        } catch (Exception e) {
        }

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
    public ModelAndView sendMessage(@RequestParam MultipartFile attachedFiles, @RequestParam String body, @RequestParam String subject, @RequestParam String to,
                              @AuthenticationPrincipal User user, @ModelAttribute("gmail") Gmail gmail, Model model) {

        ModelAndView modelAndView = new ModelAndView();
        try {

        Message message = new Message(to,subject,body, new ArrayList<>());
        if (message.getTo() == null ^ message.getTo().trim().length() == 0 ) {
            model.addAttribute("mesNotSend", true);
            modelAndView.setViewName("greeting");
            return modelAndView;
        }

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

            File f = null ;
            if (attachedFiles != null) {
                File fileDir = new File(uploadPath);
                if (!fileDir.exists()) fileDir.mkdir();

                String fileUUID = UUID.randomUUID().toString() ; // unique id file
                String resultPath = fileUUID.concat(".").concat(attachedFiles.getOriginalFilename());

                attachedFiles.transferTo(new File(uploadPath.concat("/").concat(resultPath)));
                f = new File(uploadPath +"/"+ fileUUID.concat(".").concat(attachedFiles.getOriginalFilename()));

                ArrayList<File> z = new ArrayList<>() ; z.add(f);
                message.setAttachedFiles(z);
            }
            message.setFrom(email.getEmailName());
            gmailService.sendMessage(email, message);
            model.addAttribute("mesSend", true);

            if (attachedFiles != null) if ( f != null) f.delete() ;


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
                userRepo.save(user);

            }

        } else {
            user.setActiveEmailName("none");
        }
        model.addAttribute("url","/greeting");
        model.addAttribute("errorAAA","error");
        return "rediToGoogleForm" ;
    }



}
