package com.algaworks.algafood.auth.core;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;

public class JwtCustomClaimsTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        if (oAuth2Authentication.getPrincipal() instanceof AuthUser) {
            var authUser = (AuthUser) oAuth2Authentication.getPrincipal();
            var info = new HashMap<String, Object>();

            info.put("user_id", authUser.getUserId());
            info.put("full_name", authUser.getFullName());

            var accessToken = (DefaultOAuth2AccessToken) oAuth2AccessToken;
            accessToken.setAdditionalInformation(info);
        }

        return oAuth2AccessToken;
    }
}
