package com.example.kmail.service;

import java.util.Scanner;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

public class EmailService {

    private static final String NETWORK_NAME = "Google";

    private static final String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/oauth2/v2/userinfo?alt=json";

    private static final String SCOPE = "https://mail.google.com/ https://www.googleapis.com/auth/userinfo.email";

    private static final Token EMPTY_TOKEN = null;


}