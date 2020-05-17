package com.example.kmail.controller;

import com.example.kmail.domain.Role;
import com.example.kmail.domain.User;
import com.example.kmail.repository.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users") // /user будет добавляться в мапингах к указанным путям например будет user/admin
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    UserRep userRepo;

    @GetMapping("")
    public String userList (Model model) {
        model.addAttribute("users",userRepo.findAll());
        return "userList";
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());

        return "userEditForm";
    }

    @PostMapping
    public String userSave(@RequestParam String username, @RequestParam Map<String, String> form,
                           @RequestParam("userId") User user){

        user.setUsername(username);
        user.getRoles().clear();
        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());
        for (String key : form.keySet()) {
            if (roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepo.save(user);
        return "redirect:/users";
    }


}
