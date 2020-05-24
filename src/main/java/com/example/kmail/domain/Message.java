package com.example.kmail.domain;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


@Component
public class Message {


    private String mesId;

    private String from;
    private String to;
    private String subject;
    private String snippet;
    private String body;
    private ArrayList<File> attachedFiles;
    private String date;
    private List<String> labels;

    public Message () {
    }

    public Message( String mesId, String from, String to, String subject, String snippet,String date,List<String> labels) {

        this.mesId = mesId;
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.snippet = snippet;
        this.date = date;
        this.labels = labels ;
    }

    public Message (String to, String subject, String body,ArrayList<File> attachedFiles ) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.attachedFiles = attachedFiles;
    }

    public Message(String to, String from, String subject, String body) {
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.body = body;
    }

    public String getMesId() {
        return mesId;
    }

    public void setMesId(String mesId) {
        this.mesId = mesId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ArrayList<File> getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(ArrayList<File> attachedFiles) {
        this.attachedFiles = attachedFiles;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }
}

