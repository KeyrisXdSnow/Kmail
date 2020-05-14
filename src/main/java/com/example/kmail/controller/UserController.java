package com.example.kmail.controller;

import com.example.kmail.domain.User;
import com.example.kmail.repository.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user") // /user будет добавляться в мапингах к указанным путям например будет user/admin
public class UserController {
    @Autowired
    UserRep userRepo;

    @GetMapping("")
    public String userList (Model model) {
        model.addAttribute("users",userRepo.findAll());
        return "userList.ftl";
    }


}
