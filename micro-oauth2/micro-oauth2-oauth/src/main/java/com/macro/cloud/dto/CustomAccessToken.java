package com.macro.cloud.dto;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @program: study_microservice
 * @description:
 * @author: dzp
 * @create: 2021-12-13 16:55
 **/
public class CustomAccessToken implements OAuth2AccessToken {

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }

    @Override
    public Set<String> getScope() {
        return null;
    }

    @Override
    public OAuth2RefreshToken getRefreshToken() {
        return null;
    }

    @Override
    public String getTokenType() {
        return null;
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public Date getExpiration() {
        return null;
    }

    @Override
    public int getExpiresIn() {
        return 0;
    }

    @Override
    public String getValue() {
        return null;
    }
}
