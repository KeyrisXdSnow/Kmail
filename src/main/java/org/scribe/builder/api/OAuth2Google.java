package org.scribe.builder.api;

import org.scribe.exceptions.OAuthException;
import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.model.*;
import org.scribe.oauth.OAuth20ServiceImpl;
import org.scribe.oauth.OAuthService;
import org.scribe.utils.OAuthEncoder;
import org.scribe.utils.Preconditions;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class OAuth2Google extends DefaultApi20 {

    private static final String AUTHORIZE_URL = "https://accounts.google.com/o/oauth2/auth?response_type=code&client_id=%s&redirect_uri=%s";
    private static final String SCOPED_AUTHORIZE_URL = AUTHORIZE_URL + "&scope=%s";

    @Override
    public String getAccessTokenEndpoint() {
        return "https://accounts.google.com/o/oauth2/token";
    }

    @Override
    public AccessTokenExtractor getAccessTokenExtractor() {

        return new AccessTokenExtractor() {

            @Override
            public Token extract(String response) {
                Preconditions.checkEmptyString(response, "Response body is incorrect. Can't extract a token from an empty string");

                Matcher matcher = Pattern.compile("\"access_token\"\\s*:\\s*\"([^&\"]+)\"").matcher(response);
                if (matcher.find()) {
                    String token = OAuthEncoder.decode(matcher.group(1));
                    return new Token(token, "", response);
                } else {
                    throw new OAuthException(
                            "Response body is incorrect. Can't extract a token from this: '"
                                    + response + "'", null);
                }
            }
        };
    }

    @Override
    public String getAuthorizationUrl(OAuthConfig config) {
        // Append scope if present
        if (config.hasScope()) {
            return String.format(SCOPED_AUTHORIZE_URL, config.getApiKey(),
                    OAuthEncoder.encode(config.getCallback()),
                    OAuthEncoder.encode(config.getScope()));
        } else {
            return String.format(AUTHORIZE_URL, config.getApiKey(),
                    OAuthEncoder.encode(config.getCallback()));
        }
    }

    @Override
    public Verb getAccessTokenVerb() {
        return Verb.POST;
    }

    @Override
    public OAuthService createService(OAuthConfig config) {
        return new GoogleOAuth2Service(this, config);
    }

    private static class GoogleOAuth2Service extends OAuth20ServiceImpl {

        private static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
        private static final String GRANT_TYPE = "grant_type";
        private final DefaultApi20 api;
        private final OAuthConfig config;

        public GoogleOAuth2Service(DefaultApi20 api, OAuthConfig config) {
            super(api, config);
            this.api = api;
            this.config = config;
        }

        @Override
        public Token getAccessToken(Token requestToken, Verifier verifier) {
            OAuthRequest request = new OAuthRequest(
                    api.getAccessTokenVerb(),
                    api.getAccessTokenEndpoint()
            );

            request.addOAuthParameter(
                    OAuthConstants.CALLBACK,
                    config.getCallback())
            ;

            switch (api.getAccessTokenVerb()) {
                case POST: {
                    request.addBodyParameter(OAuthConstants.CLIENT_ID,
                            config.getApiKey());
                    request.addBodyParameter(OAuthConstants.CLIENT_SECRET,
                            config.getApiSecret());
                    request.addBodyParameter(OAuthConstants.CODE,
                            verifier.getValue());
                    request.addBodyParameter(OAuthConstants.REDIRECT_URI,
                            config.getCallback());
                    request.addBodyParameter(OAuthConstants.SCOPE, config.getScope());
                    request.addBodyParameter(GRANT_TYPE,
                            GRANT_TYPE_AUTHORIZATION_CODE);
                    try {
                        request.addBodyParameter(OAuthConstants.CODE,
                                URLDecoder.decode(verifier.getValue(), "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case GET:
                default: {
                    request.addQuerystringParameter(OAuthConstants.CLIENT_ID,
                            config.getApiKey());
                    request.addQuerystringParameter(OAuthConstants.CLIENT_SECRET,
                            config.getApiSecret());
                    request.addQuerystringParameter(OAuthConstants.CODE,
                            verifier.getValue());
                    request.addQuerystringParameter(OAuthConstants.REDIRECT_URI,
                            config.getCallback());

                    if (config.hasScope())
                        request.addQuerystringParameter(OAuthConstants.SCOPE,
                                config.getScope());
                }
            }

            Response response = request.send();
            try {
                return api.getAccessTokenExtractor().extract(response.getBody());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;

        }
    }

}