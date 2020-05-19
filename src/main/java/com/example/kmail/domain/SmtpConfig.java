package com.example.kmail.domain;

import javax.persistence.*;

@Entity
@Table(name = "supported_smtp")

public class SmtpConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private String postServiceDomain ;
    private String host;
    private int port;
    private String protocol;
    private boolean auth;
    private boolean enable;
    private boolean debug;


    public SmtpConfig () {
    }

    public SmtpConfig(Long id, String host, int port, String protocol, boolean auth, boolean enable, boolean debug) {
        this.id = id;
        this.host = host;
        this.port = port;
        this.protocol = protocol;
        this.auth = auth;
        this.enable = enable;
        this.debug = debug;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostServiceDomain() {
        return postServiceDomain;
    }

    public void setPostServiceDomain(String postServiceDomain) {
        this.postServiceDomain = postServiceDomain;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
