package com.example.kmail.service;

import com.example.kmail.domain.Email;
import com.example.kmail.domain.User;
import com.example.kmail.repository.EmailRepo;
import com.example.kmail.repository.UserRepo;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.OAuth2Google;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.example.kmail.config.OAuth2Config.*;

@Service
public class MailService {

    @Autowired
    private EmailRepo emailRepo;
    @Autowired
    private UserRepo userRepo;

    private final static Token EMPTY_TOKEN = null;
    private OAuthService service;

    /**
     * Call Gmail authorization Form
     * @return gmail authorization form url
     */
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


    /**
     * authorize Gmail
     * @param code - authorization code
     * @param user - active code
     * @return authorize email
     * @throws ParseException
     */
    public Email initAuthorize(String code, User user) throws ParseException {

        Verifier verifier;
        Token accessToken;

        verifier = new Verifier(code);
        accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);

        OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);

        service.signRequest(accessToken, request);
        Response response = request.send();
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(response.getBody());

        return new Email(
                String.valueOf(jsonObject.get("id")),
                String.valueOf(jsonObject.get("email")),
                accessToken.getToken(),
                user
        );

    }

    /**
     * LogOut from Gmail
     * @param email - gmail we want logout
     * @return - logout url
     */
    public HttpPost removeUserEmailAccess (Email email) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(LOGOUT_URL);
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("token", email.getAccessToken()));
        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        return  httpPost;

        //"https://accounts.google.com/o/oauth2/revoke?token=";
    }

    /**
     * add mail to database
     * @param user - active user
     * @param email - mail we want to add to the database
     */
    public void addMail(User user, Email email) {

        Email activeEmail = emailRepo.findByIsActiveAndUserId(true, user.getId());
        String activeEmailName = "" ;

        if (activeEmail == null) {
            activeEmail = emailRepo.findByEmailIdAndUserId(email.getEmailId(), user.getId());
            if (activeEmail == null) {
                email.setActive(true);
                activeEmailName = email.getEmailName();
                emailRepo.save(email);
            } else {
                activeEmail.setActive(true);
                activeEmail.setEmailName(email.getEmailName());
                activeEmail.setAccessToken(email.getAccessToken());
                emailRepo.save(activeEmail);

                activeEmailName = activeEmail.getEmailName();
            }
        } else {
            if (activeEmail.getEmailId().equals(email.getEmailId())) {
                activeEmail.setEmailName(email.getEmailName());
                activeEmail.setAccessToken(email.getAccessToken());
                emailRepo.save(activeEmail);

                activeEmailName = activeEmail.getEmailName();
            } else {
                activeEmail.setActive(false);
                email.setActive(true);

                emailRepo.save(activeEmail);
                emailRepo.save(email);


                activeEmailName = email.getEmailName();
            }

        }
        user.setActiveEmailName(activeEmailName);
    }


}