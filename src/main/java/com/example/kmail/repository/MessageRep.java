package com.example.kmail.repository;

import com.example.kmail.domain.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRep extends CrudRepository<Message,Long> {
    List<Message> findByTag(String tag);
}
