package com.macro.cloud.config;

import com.macro.cloud.doadmin.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @program: study_microservice
 * @description: swagger配置
 * @author: dzp
 * @create: 2021-12-10 13:14
 **/
@Configuration
@EnableSwagger2
public class Swagger2Config extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.macro.cloud.controller")
                .title("macro认证")
                .description("认证中心相关接口文档")
                .contactName("macro")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
