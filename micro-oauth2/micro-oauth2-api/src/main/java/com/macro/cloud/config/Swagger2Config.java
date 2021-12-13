package com.macro.cloud.config;

import com.macro.cloud.doadmin.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author liuzhixiang liuzhixiang
 * DATE   2021/8/31
 */
@Configuration
@EnableSwagger2
public class Swagger2Config extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.macro.cloud.controller")
                .title("macro-api模块")
                .description("macro-api模块中的一些示例")
                .contactName("macro")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }

}
