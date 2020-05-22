package com.example.kmail.repository;

import com.example.kmail.domain.Notes;
import com.example.kmail.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRep extends CrudRepository<Notes,Long> {
    List<Notes> findByTag(String tag);
    List<Notes> findByAuthor(User user);
    List<Notes> findByAuthorAndTag(User user,String tag);
}
