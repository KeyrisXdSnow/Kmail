package com.example.kmail.controller;

import com.example.kmail.domain.Message;
import com.example.kmail.domain.User;
import com.example.kmail.repository.MessageRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Controller
public class MainController {

    @Autowired
    private MessageRep messageRepo;
    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String mainForm(Model model) {

        return "greeting";
    }

    @GetMapping("/greeting")
    public String greeting(Model model) {
        return "greeting";
    }

    @GetMapping("/mainForm")
    public String main(@RequestParam(required = false,defaultValue = "") String filter, Model model) {

        Iterable<Message> messages;

        if (filter != null && !filter.isEmpty()) {
            messages = messageRepo.findByTag(filter);
        } else {
            messages = messageRepo.findAll();
        }

        model.addAttribute("messages", messages);
        model.addAttribute("filter",filter);

        return "mainForm";
    }

    @PostMapping("/add")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text, @RequestParam String tag,
            @RequestParam MultipartFile file,
            Model model) throws IOException {
        Message message = new Message(text, tag,user);
        if (file != null) {
            File fileDir = new File(uploadPath);
            if (!fileDir.exists()) fileDir.mkdir();

            String fileUUID = UUID.randomUUID().toString() ; // unique id file
            String resultPath = fileUUID.concat(".").concat(file.getOriginalFilename());

            file.transferTo(new File(uploadPath.concat("/").concat(resultPath)));

            message.setFileName(resultPath);
        }

        messageRepo.save(message);

        Iterable<Message> messages = messageRepo.findAll();

        model.addAttribute("messages", messages);

        return "mainForm";
    }

}


