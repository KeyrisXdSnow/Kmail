package com.example.kmail.repository;


import com.example.kmail.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRep extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByActivationCode(String code);
}
