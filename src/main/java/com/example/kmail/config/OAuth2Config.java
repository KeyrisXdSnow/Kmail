package com.example.kmail.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class OAuth2Config {

    public static final String NETWORK_NAME = "Google";

    public static final String apiKey = "885115375507-t5qfmjcb5ahmc8cf67i1kvcdnlcher6q.apps.googleusercontent.com" ;
    public static final String apiSecret = "mGzuPwALLlznKRxDVnJrv5R1";

    public static final String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/oauth2/v2/userinfo?alt=json";
    public static final String callbackUrl = "http://localhost:8080/mainForm";

    public static final String SCOPE = "https://mail.google.com/ https://www.googleapis.com/auth/userinfo.email";

}