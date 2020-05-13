package com.example.kmail.service;

import com.example.kmail.repository.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
//
//    private final UserRep userRep; // новый способ заменяет AutoWired
//
//    public UserService(UserRep userRep) {
//        this.userRep = userRep;
//    }

    @Autowired
    private UserRep userRep;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRep.findByUsername(username);
    }
}
