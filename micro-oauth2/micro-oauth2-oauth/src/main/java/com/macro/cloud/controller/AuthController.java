package com.macro.cloud.controller;

import com.macro.cloud.api.CommonResult;
import com.macro.cloud.dto.Oauth2TokenDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

/**
 * @program: study_microservice
 * @description: 自定义oauth2获取令牌接口
 * @author: dzp
 * @create: 2021-12-09 17:12
 **/
@RestController
@RequestMapping("/oauth")
@Api(value = "自定义oauth2获取令牌接口", tags = "自定义oauth2获取令牌接口")
public class AuthController {

    @Autowired
    private TokenEndpoint tokenEndpoint;

    /**
     * Oauth2登录认证
     */
    @ApiOperation(value = "Oauth2登录认证")
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public CommonResult<Oauth2TokenDto> postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        Oauth2TokenDto oauth2TokenDto = Oauth2TokenDto.builder()
                .token(oAuth2AccessToken.getValue())
                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .tokenHead("Bearer ").build();

        return CommonResult.success(oauth2TokenDto);
    }
}
