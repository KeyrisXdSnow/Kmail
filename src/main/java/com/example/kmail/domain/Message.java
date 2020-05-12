package com.example.kmail.domain;

import javax.persistence.*;

@Entity
public class Message {
    /** spring будет сам генерировать значение поля id **/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id ;

    private String text ;
    private String tag;

    public Message () {
    }

    public Message (String text, String tag) {
        this.text = text;
        this.tag = tag ;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
