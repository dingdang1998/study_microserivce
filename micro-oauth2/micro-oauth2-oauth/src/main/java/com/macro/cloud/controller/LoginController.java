package com.macro.cloud.controller;

import com.macro.cloud.api.CommonResult;
import com.macro.cloud.dto.CustomAccessToken;
import com.macro.cloud.dto.Oauth2TokenDto;
import com.macro.cloud.dto.ReqTokenDto;
import com.macro.cloud.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: study_microservice
 * @description:
 * @author: dzp
 * @create: 2021-12-13 16:46
 **/
@Api(value = "认证", tags = "登录认证接口")
@RestController
@Slf4j
public class LoginController {

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation("自定义登录获取token")
    public CommonResult<Oauth2TokenDto> login(ReqTokenDto reqTokenDto) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("client_id", reqTokenDto.getClient_id());
        parameters.put("client_secret", reqTokenDto.getClient_secret());
        parameters.put("grant_type", reqTokenDto.getGrant_type());
        parameters.put("username", reqTokenDto.getUsername());
        parameters.put("password", reqTokenDto.getPassword());
        parameters.put("refresh_token", reqTokenDto.getRefresh_token());
        OAuth2AccessToken oAuth2AccessToken = new CustomAccessToken();
        try {
            oAuth2AccessToken = authService.getAccessToken(parameters).getBody();
        } catch (Exception e) {
            log.error("登录错误", e);
            System.out.println(e.getMessage());
            if (e.getMessage().startsWith("[401]") || e.getMessage().startsWith("[400]")) {
                System.out.println("用户名密码错误！");
                return CommonResult.failed("用户名密码错误！");
            }
        }
        Oauth2TokenDto oauth2TokenDto = Oauth2TokenDto.builder()
                .token(oAuth2AccessToken.getValue())
                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .tokenHead("Bearer ").build();
        return CommonResult.success(oauth2TokenDto);
    }

}

