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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    public Message () {
    }

    public Message (String text, String tag, User author) {
        this.text = text;
        this.tag = tag ;
        this.author = author;
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

    public User getUser() {
        return author;
    }

    public void setUser(User author) {
        this.author = author;
    }

    public String getAuthorName () {
        return author.getUsername() != null ? author.getUsername() : "<none>";
    }
}
