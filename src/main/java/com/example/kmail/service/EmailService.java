package com.example.kmail.service;

import com.example.kmail.domain.Email;
import com.example.kmail.repository.EmailRepo;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.OAuth2Google;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.kmail.config.OAuth2Config.*;

@Service
public class EmailService {

    @Autowired
    private EmailRepo emailRepo;



    private final Token EMPTY_TOKEN = null;
    private OAuthService service;

    public String getAuthorizationForm() {

        service = new ServiceBuilder()
                .provider(OAuth2Google.class)
                .apiKey(apiKey)
                .apiSecret(apiSecret)
                .callback(callbackUrl)
                .scope(SCOPE)
                .build();

        return service.getAuthorizationUrl(null);
    }


    public Email addUserEmail(String code) throws ParseException {

        Verifier verifier;
        Token accessToken;

        verifier = new Verifier(code);
        accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);

        OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);

        service.signRequest(accessToken, request);
        Response response = request.send();

        System.out.println(accessToken.getToken());
        System.out.println("Got it! Lets see what we found...");
        System.out.println();
        System.out.println(response.getCode());
        System.out.println(response.getBody());
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(response.getBody());

        Email email = new Email(String.valueOf(jsonObject.get("id")),String.valueOf(jsonObject.get("email")),accessToken.getToken());
        return email;

    }

    public String removeUserEmailAccess (Email email) {
        String url = LOGOUT_URL + email.getAccessToken();
        emailRepo.delete(email);
        return  url;

    }


}