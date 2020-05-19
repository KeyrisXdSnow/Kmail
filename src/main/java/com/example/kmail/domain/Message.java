package com.example.kmail.domain;

import javax.persistence.*;
import java.io.File;
import java.util.ArrayList;


public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id ;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private String sender;
    private String recipient;
    private String subject;
    private String messageText ;
    private ArrayList<File> attachedFiles;

    public Message () {
    }

    public Message(String sender, String recipient, String subject, String messageText, ArrayList<File> attachedFiles) {
        this.sender = sender;
        this.recipient = recipient;
        this.subject = subject;
        this.messageText = messageText;
        this.attachedFiles = attachedFiles;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public ArrayList<File> getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(ArrayList<File> attachedFiles) {
        this.attachedFiles = attachedFiles;
    }
}
