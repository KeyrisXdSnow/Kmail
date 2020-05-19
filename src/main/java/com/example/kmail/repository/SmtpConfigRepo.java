package com.example.kmail.repository;

import com.example.kmail.domain.SmtpConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmtpConfigRepo extends CrudRepository<SmtpConfig,Long> {
  // List<SmtpConfig> findById (Long id);
    List<SmtpConfig> findByHost (String host);
    SmtpConfig findByPostServiceDomain(String postServiceDomain);
}
