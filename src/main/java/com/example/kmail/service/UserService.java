package com.example.kmail.service;

import com.example.kmail.domain.Role;
import com.example.kmail.domain.User;
import com.example.kmail.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
@Component("sendEmail")
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MailSender mailSender;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username) ;

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public boolean addUser (User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) return false;

        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());

        userRepo.save(user);

        if (!user.getLoginEmail().isEmpty()){
            String message = String.format(
                    "Hello, %s! \n " +
                    "Welcome to KMail. Please, visit next link to activate " +
                            "your profile <a href='http://localhost:8080/activate/%s'> put here </a>",
                    user.getUsername(),user.getActivationCode()
            );
            user.setActive(true);
            user.setActivationCode(null);
            mailSender.send(user.getLoginEmail(),"Activation Kmail service",message);
        }


        return true;

    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);

        if (user == null){
            return false;
        }

        user.setActive(true);
        user.setActivationCode(null);
        userRepo.save(user);
        return true;
    }

}