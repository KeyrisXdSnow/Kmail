package com.example.kmail.repository;

import com.example.kmail.domain.Email;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmailRepo extends CrudRepository<Email,Long> {
    List<Email> findByEmailName(String emailName);
    Email findByEmailNameAndUserId (String emailName, Long userId);
    Email findByEmailIdAndUserId (String emailId, Long userId);
    Email findByIsActiveAndUserId (boolean isActive, Long id);

}
