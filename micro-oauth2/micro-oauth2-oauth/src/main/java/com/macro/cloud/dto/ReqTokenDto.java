package com.macro.cloud.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: study_microservice
 * @description:
 * @author: dzp
 * @create: 2021-12-13 16:52
 **/
@Data
public class ReqTokenDto {
    @ApiModelProperty(value = "客户端", example = "client-app", required = true)
    private String client_id = "client-app";

    @ApiModelProperty(value = "认证类型", example = "password", required = true)
    private String grant_type = "password";

    @ApiModelProperty(value = "客户端密钥", example = "123456", required = true)
    private String client_secret = "123456";

    @ApiModelProperty(value = "用户名", example = "macro", required = false)
    private String username;

    @ApiModelProperty(value = "密码", example = "123456", required = false)
    private String password;

    @ApiModelProperty(value = "refresh_token", example = "", required = false)
    private String refresh_token;
}
