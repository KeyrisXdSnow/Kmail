package com.example.kmail.repository;

import com.example.kmail.domain.Email;
import com.example.kmail.domain.Notes;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmailRepo extends CrudRepository<Email,Long> {
    List<Email> findByEmailName(String emailName);
    Email findByIsActive (boolean isActive);
}
