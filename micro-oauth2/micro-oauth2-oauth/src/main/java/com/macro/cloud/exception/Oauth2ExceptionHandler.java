package com.macro.cloud.exception;

import com.macro.cloud.api.CommonResult;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: study_microservice
 * @description: 全局处理oauth2抛出的异常
 * @author: dzp
 * @create: 2021-12-09 17:09
 **/
@ControllerAdvice
public class Oauth2ExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = OAuth2Exception.class)
    public CommonResult<String> handleOauth2(OAuth2Exception e) {
        return CommonResult.failed(e.getMessage());
    }
}
