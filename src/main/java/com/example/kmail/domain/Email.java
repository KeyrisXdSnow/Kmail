package com.example.kmail.domain;

import javax.persistence.*;

@Entity
@Table (name = "usr_emails")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    private String emailId ;
    private String emailName ;
    private boolean isActive = false;
    private String accessToken ;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Email() {
    }

    public Email(String emailId, String emailName, String accessToken) {
        this.emailId = emailId;
        this.emailName = emailName;
        this.accessToken = accessToken;
    }

    public Email(String emailId, String emailName,String accessToken, User user) {
        this.emailId = emailId;
        this.emailName = emailName;
        this.accessToken = accessToken;
        this.user = user;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getEmailName() {
        return emailName;
    }

    public void setEmailName(String emailName) {
        this.emailName = emailName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
