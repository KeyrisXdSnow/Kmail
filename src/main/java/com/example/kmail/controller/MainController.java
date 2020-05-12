package com.example.kmail.controller;

import com.example.kmail.domain.Message;
import com.example.kmail.repository.MessageRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private MessageRep messageRepo;

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Map<String, Object> model
    ) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping ("/mainForm")
    public String main(Map<String, Object> model) {
        Iterable<Message> messages = messageRepo.findAll();

        model.put("messages", messages);

        return "mainForm";
    }

    @PostMapping ("/mainForm")
    public String add(@RequestParam String text, @RequestParam String tag, Map<String, Object> model) {
        Message message = new Message(text, tag);
//id не генерируется
        messageRepo.save(message);

        Iterable<Message> messages = messageRepo.findAll();

        model.put("messages", messages);

        return "mainForm";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Message> messages;

        if (filter != null && !filter.isEmpty()) {
            messages = messageRepo.findByTag(filter);
        } else {
            messages = messageRepo.findAll();
        }

        model.put("messages", messages);

        return "mainForm";
    }
}

//<!-- security -->
//<dependency>
//<groupId>org.springframework.boot</groupId>
//<artifactId>spring-boot-starter-security</artifactId>
//</dependency>
//<dependency>
//<groupId>org.springframework.security</groupId>
//<artifactId>spring-security-core</artifactId>
//</dependency>
//<dependency>
//<groupId>org.springframework.security</groupId>
//<artifactId>spring-security-web</artifactId>
//</dependency>
//<dependency>
//<groupId>org.springframework.security</groupId>
//<artifactId>spring-security-config</artifactId>
//</dependency>
//<dependency>
//<groupId>org.springframework.security</groupId>
//<artifactId>spring-security-test</artifactId>
//<scope>test</scope>
//</dependency>
