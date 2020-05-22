package com.example.kmail.domain;

public class MessageContent {

    private String messageId ;

    private String text;
    private String date;
    private String from;
    private String subject;

    public MessageContent(String messageId, String text, String date, String from, String subject) {
        this.messageId = messageId;
        this.text = text;
        this.date = date;
        this.from = from;
        this.subject = subject;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

}
